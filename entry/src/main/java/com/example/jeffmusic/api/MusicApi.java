package com.example.jeffmusic.api;

import com.example.jeffmusic.model.MusicModel;
import com.example.jeffmusic.model.PlayList;
import poetry.jianjia.Call;
import poetry.jianjia.http.*;

import java.util.List;
import java.util.Map;

public interface MusicApi {

    @GET("music/all")
    Call<List<MusicModel>> getSongs(@Headers("Authorization") String token,@QueryMap Map<String, String> map);

    @GET("music/playlist")
    Call<List<PlayList>> getPlayList(@Headers("Authorization") String token);

    @GET("music/collect")
    Call<List<MusicModel>> getPlayListSongs(@Headers("Authorization") String token, @Query("play_list_id") int id);

}
