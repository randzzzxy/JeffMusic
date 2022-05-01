package com.example.jeffmusic.procotol;

import com.example.jeffmusic.model.UserModel;

public interface PlayerCallBack {
    // 播放器进度
    void onProcess(int process);

    // 当前歌曲
    void onCurrentSongMessage(String name, UserModel author, boolean isPlaying, String coverUrl);
}
