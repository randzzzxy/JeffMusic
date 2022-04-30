package com.example.jeffmusic.procotol;

import com.example.jeffmusic.model.MusicModel;

import java.util.List;

public interface IMusicPlayer {
    //设置播放列表
    void setPlayList(List<MusicModel> list);

    //获取播放列表
    List<MusicModel> getPlayList();

    //播放
    void startPlaying();

    //暂停
    void pausePlaying();

    //下一首歌
    void nextPlaying();

    //切换播放模式
    void switchPlayMode();

    //获取播放模式
    void getPlayMode();
}
