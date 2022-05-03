package com.example.jeffmusic.player;

import com.example.jeffmusic.MyApplication;
import com.example.jeffmusic.ResourceTable;
import com.example.jeffmusic.model.Collect;
import com.example.jeffmusic.model.MusicModel;
import com.example.jeffmusic.model.PlayList;
import com.example.jeffmusic.procotol.IMusicPlayer;
import com.example.jeffmusic.procotol.PlayerCallBack;
import com.example.jeffmusic.provider.MusicItemProvider;
import ohos.agp.components.*;
import ohos.agp.window.dialog.CommonDialog;
import ohos.agp.window.dialog.IDialog;
import ohos.agp.window.dialog.ToastDialog;
import ohos.app.Context;
import ohos.app.dispatcher.task.TaskPriority;
import ohos.media.common.Source;
import ohos.media.player.Player;
import poetry.jianjia.Call;
import poetry.jianjia.Callback;
import poetry.jianjia.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class MusicPlayer implements IMusicPlayer {
    private Player mPlayer;
    private Context mContext;
    private Source mPlayerSource;
    private List<MusicModel> mData = new ArrayList<>();
    private int mCurrentPos;
    private List<PlayerCallBack> mPlayerCallBacks = new ArrayList<>();
    private  boolean isPlaying;
    private Player.IPlayerCallback callback = new Player.IPlayerCallback() {
        @Override
        public void onPrepared() {

        }

        @Override
        public void onMessage(int i, int i1) {

        }

        @Override
        public void onError(int i, int i1) {

        }

        @Override
        public void onResolutionChanged(int i, int i1) {

        }

        @Override
        public void onPlayBackComplete() {

        }

        @Override
        public void onRewindToComplete() {

        }

        @Override
        public void onBufferingChange(int i) {

        }

        @Override
        public void onNewTimedMetaData(Player.MediaTimedMetaData mediaTimedMetaData) {

        }

        @Override
        public void onMediaTimeIncontinuity(Player.MediaTimeInfo mediaTimeInfo) {

        }
    };

    public boolean isPlaying() {
        return isPlaying;
    }

    public MusicPlayer(Context context) {
        mContext = context;
        mPlayer = new Player(mContext);
        mPlayer.setPlayerCallback(callback);
    }

    private void initNetSource(String path) {
        if (mContext == null || path == null) {
            return;
        }
        mPlayerSource = new Source(MyApplication.RESOURCE_MUSIC_URL_PRE + path);
        mPlayer.setSource(mPlayerSource);
    }

    @Override
    public void setPlayList(List<MusicModel> list, int currentPos) {
        mData.clear();
        mData.addAll(list);
        mCurrentPos = currentPos;
        initNetSource(mData.get(currentPos).getSongUrl());
    }

    @Override
    public List<MusicModel> getPlayList() {
        return null;
    }

    @Override
    public void startPlaying() {
        if (mPlayer != null && mData.size() != 0) {
            mContext.getGlobalTaskDispatcher(TaskPriority.DEFAULT).asyncDispatch(() -> {
                isPlaying = true;
                mPlayer.prepare();
                mPlayer.play();
                mContext.getUITaskDispatcher().asyncDispatch(() -> {
                    dispatchEvent();
                });
            });
        }
    }

    @Override
    public void pausePlaying() {
        if (mPlayer != null) {
            mContext.getGlobalTaskDispatcher(TaskPriority.DEFAULT).asyncDispatch(() -> {
                isPlaying = false;
                mPlayer.pause();
                mContext.getUITaskDispatcher().asyncDispatch(() -> {
                    dispatchEvent();
                });
            });
        }
    }

    @Override
    public void nextPlaying() {
        if (mPlayer != null && mData.size() != 0) {
            mContext.getGlobalTaskDispatcher(TaskPriority.DEFAULT).asyncDispatch(() -> {
                mPlayer.reset();
                initNetSource(mData.get(mCurrentPos = (mCurrentPos + 1)  % mData.size()).getSongUrl());
                mPlayer.prepare();
                mPlayer.play();
                mContext.getUITaskDispatcher().asyncDispatch(() -> {
                    dispatchEvent();
                });
            });
        }
    }

    @Override
    public void previousPlaying() {
        if (mPlayer != null && mData.size() != 0) {
            mContext.getGlobalTaskDispatcher(TaskPriority.DEFAULT).asyncDispatch(() -> {
                mPlayer.reset();
                initNetSource(mData.get(mCurrentPos = (mCurrentPos - 1 + mData.size())  % mData.size()).getSongUrl());
                mPlayer.prepare();
                mPlayer.play();
                mContext.getUITaskDispatcher().asyncDispatch(() -> {
                    dispatchEvent();
                });
            });
        }
    }

    private void dispatchEvent() {
        if (mPlayerCallBacks != null) {
            for (int i = 0; i < mPlayerCallBacks.size(); i++) {
                mPlayerCallBacks.get(i).onCurrentSongMessage(mData.get(mCurrentPos).name
                        , null, isPlaying
                        , mData.get(mCurrentPos).coverUrl);
            }
        }
    }

    public MusicModel getCurrentSongInfo() {
        return mData.get(mCurrentPos);
    }

    @Override
    public void switchPlayMode() {

    }

    @Override
    public void getPlayMode() {

    }

    @Override
    public void setProgress(int progress) {

    }

    public static void showPlayListDialog(Context context, String playlistName, List<MusicModel> data) {
        CommonDialog dialog = new CommonDialog(context);
        ComponentContainer container = (ComponentContainer) LayoutScatter.getInstance(context).parse(ResourceTable.Layout_dialog_playlist_music
                , null, false);
        dialog.setContentCustomComponent(container);
        Text playlistText = container.findComponentById(ResourceTable.Id_playlist_name);
        ListContainer listComponent = container.findComponentById(ResourceTable.Id_list_container);
        MusicItemProvider musicItemProvider = new MusicItemProvider(context);
        listComponent.setItemProvider(musicItemProvider);
        playlistText.setText(playlistName);
        musicItemProvider.setData(data);
        dialog.show();
    }

    public static void showPlayListsDialog(Context context, String playlistName, List<PlayList> data, MusicModel musicModel) {
        if (context == null || data == null) {
            return;
        }
        AtomicBoolean canCollect = new AtomicBoolean(false);
        CommonDialog dialog = new CommonDialog(context);
        ComponentContainer container = (ComponentContainer) LayoutScatter.getInstance(context).parse(ResourceTable.Layout_dialog_playlists
                , null, false);
        dialog.setContentCustomComponent(container);
        Text playlistText = container.findComponentById(ResourceTable.Id_playlist_name);
        RadioContainer radioContainer = container.findComponentById(ResourceTable.Id_list_container);
        radioContainer.setOrientation(Component.VERTICAL);
        RadioButton[] radioButtons = new RadioButton[data.size()];
        for (int i = 0; i < data.size(); i++) {
            RadioButton radioButton = new RadioButton(context);
            radioButton.setText(data.get(i).name);
            radioContainer.addComponent(radioButton);
            radioButtons[i] = radioButton;
        }
        radioContainer.setMarkChangedListener((radioContainer1, i) -> {
            canCollect.set(true);
        });
        dialog.setButton(IDialog.BUTTON1, "取消", new IDialog.ClickedListener() {
            @Override
            public void onClick(IDialog iDialog, int i) {
                dialog.destroy();
            }
        });
        dialog.setButton(IDialog.BUTTON3, "收藏", new IDialog.ClickedListener() {
            @Override
            public void onClick(IDialog iDialog, int i) {
                if (canCollect.get()) {
                    MyApplication.getInstance().getMusicApi().collectSong(MyApplication.getInstance().getToken()
                    , new Collect(data.get(radioContainer.getMarkedButtonId()).id, musicModel.id))
                            .enqueue(new Callback<List<MusicModel>>() {
                                @Override
                                public void onResponse(Call<List<MusicModel>> call, Response<List<MusicModel>> response) {
                                    if (response.code() == 200) {
                                        new ToastDialog(context)
                                                .setText("收藏成功")
                                                .show();
                                    } else {
                                        new ToastDialog(context)
                                                .setText("收藏失败")
                                                .show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<MusicModel>> call, Throwable throwable) {
                                    new ToastDialog(context)
                                            .setText("收藏失败")
                                            .show();
                                }
                            });
                }
            }
        });
        playlistText.setText(playlistName);
        dialog.show();
    }

    @Override
    public void addPlayerCallBack(PlayerCallBack progressCallBack) {
        mPlayerCallBacks.add(progressCallBack);
    }

}
