package com.example.jeffmusic.player;

import com.example.jeffmusic.MyApplication;
import com.example.jeffmusic.model.MusicModel;
import com.example.jeffmusic.procotol.IMusicPlayer;
import com.example.jeffmusic.procotol.PlayerCallBack;
import ohos.app.Context;
import ohos.app.dispatcher.task.TaskPriority;
import ohos.media.common.Source;
import ohos.media.player.Player;

import java.util.ArrayList;
import java.util.List;

public class MusicPlayer implements IMusicPlayer {
    private Player mPlayer;
    private Context mContext;
    private Source mPlayerSource;
    private List<MusicModel> mData = new ArrayList<>();
    private int mCurrentPos;
    private PlayerCallBack mPlayerCallBack;
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
                    mPlayerCallBack.onCurrentSongMessage(mData.get(mCurrentPos).name
                            , null, isPlaying
                            , mData.get(mCurrentPos).coverUrl);
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
                    mPlayerCallBack.onCurrentSongMessage(mData.get(mCurrentPos).name
                            , null, isPlaying
                            , mData.get(mCurrentPos).coverUrl);
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
                    mPlayerCallBack.onCurrentSongMessage(mData.get(mCurrentPos).name
                            , null, isPlaying
                            , mData.get(mCurrentPos).coverUrl);
                });
            });
        }
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

    @Override
    public void setProgressCallBack(PlayerCallBack progressCallBack) {
        mPlayerCallBack = progressCallBack;
    }
}
