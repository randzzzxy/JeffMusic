package com.example.jeffmusic.api;

import com.example.jeffmusic.model.MusicModel;
import com.example.jeffmusic.model.PlayList;
import poetry.jianjia.Call;
import poetry.jianjia.http.*;

import java.util.List;
import java.util.Map;

public interface MusicApi {

    @GET("music/all")
    Call<List<MusicModel>> getSongs(@Header("Authorization") String token,@QueryMap Map<String, String> map);

    @GET("music/playlist")
    Call<List<PlayList>> getPlayList(@Header("Authorization") String token);

    @GET("music/collect")
    Call<List<MusicModel>> getPlayListSongs(@Header("Authorization") String token, @Query("play_list_id") int id);

}
