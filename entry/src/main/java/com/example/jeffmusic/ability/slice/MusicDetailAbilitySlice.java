package com.example.jeffmusic.ability.slice;

import com.bumptech.glide.Glide;
import com.example.jeffmusic.MyApplication;
import com.example.jeffmusic.ResourceTable;
import com.example.jeffmusic.ability.CommentDetailAbility;
import com.example.jeffmusic.model.MusicModel;
import com.example.jeffmusic.model.PlayList;
import com.example.jeffmusic.model.UserModel;
import com.example.jeffmusic.player.MusicPlayer;
import com.example.jeffmusic.procotol.PlayerCallBack;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Component;
import ohos.agp.components.Image;
import ohos.agp.components.Text;
import ohos.agp.window.dialog.ToastDialog;
import poetry.jianjia.Call;
import poetry.jianjia.Callback;
import poetry.jianjia.Response;

import java.util.List;

public class MusicDetailAbilitySlice extends AbilitySlice {
    MusicPlayer mPlayer;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_music_detail);
        initView();
    }

    private void initView() {
        mPlayer = MyApplication.getInstance().getPlayer();
        MusicModel musicModel = mPlayer.getCurrentSongInfo();
        if (musicModel == null) {
            return;
        }
        Text musicName = findComponentById(ResourceTable.Id_music_name);
        musicName.setText(musicModel.getName());
        Image cover = findComponentById(ResourceTable.Id_cover_image);
        Glide.with(getContext()).load(musicModel.getCoverUrl()).into(cover);
        initLyricsView(findComponentById(ResourceTable.Id_lyrics_text));
        //音乐播放操作区
        Image loopModeImage = findComponentById(ResourceTable.Id_loop_button);
        Image previousImage = findComponentById(ResourceTable.Id_previous_song_button);
        Image nextImage = findComponentById(ResourceTable.Id_next_song_button);
        Image playingImage = findComponentById(ResourceTable.Id_playing_button);
        Image playlistImage = findComponentById(ResourceTable.Id_play_list_button);
        loopModeImage.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                mPlayer.switchPlayMode();
            }
        });
        playlistImage.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                MusicPlayer.showPlayListDialog(getContext(),"播放列表", mPlayer.getPlayList());
            }
        });
        previousImage.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                mPlayer.previousPlaying();
            }
        });
        nextImage.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                mPlayer.nextPlaying();
            }
        });
        mPlayer.addPlayerCallBack(new PlayerCallBack() {
            @Override
            public void onProcess(int process) {

            }

            @Override
            public void onCurrentSongMessage(String name, UserModel author, boolean isPlaying, String coverUrl) {
                musicName.setText(name);
                if (getContext() == null) {
                    return;
                }
                Glide.with(getContext()).load(coverUrl).into(cover);
                if (isPlaying) {
                    playingImage.setPixelMap(ResourceTable.Media_pause_icon);
                } else {
                    playingImage.setPixelMap(ResourceTable.Media_start_icon);
                }
            }
        });

        //用户功能操作区
        Image downloadImage = findComponentById(ResourceTable.Id_down_load_button);
        Image collectImage = findComponentById(ResourceTable.Id_collect_button);
        Image commentImage = findComponentById(ResourceTable.Id_comment_button);
        commentImage.setClickedListener(component -> startCommentDetailAbility());
        collectImage.setClickedListener(component -> MyApplication.getInstance().getMusicApi().getPlayList(MyApplication.getInstance().getToken()).enqueue(new Callback<List<PlayList>>() {
            @Override
            public void onResponse(Call<List<PlayList>> call, Response<List<PlayList>> response) {
                if (response.code() == 200) {
                    MusicPlayer.showPlayListsDialog(getContext(), "收藏歌曲", response.body(), mPlayer.getCurrentSongInfo());
                } else {
                    new ToastDialog(getContext())
                            .setText("加载歌单失败")
                            .show();
                }
            }

            @Override
            public void onFailure(Call<List<PlayList>> call, Throwable throwable) {
                new ToastDialog(getContext())
                        .setText("加载歌单失败")
                        .show();
            }
        }));
        findComponentById(ResourceTable.Id_back_button).setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                terminateAbility();
            }
        });

    }

    private void initLyricsView(Text text) {
    }

    //音乐评论页
    private void startCommentDetailAbility() {
        Intent intent = new Intent();
        Operation operation = new Intent.OperationBuilder().withDeviceId("")
                .withBundleName(getBundleName())
                .withAbilityName(CommentDetailAbility.class)
                .build();
        intent.setOperation(operation);
        startAbility(intent);
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
