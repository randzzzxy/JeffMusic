package com.example.jeffmusic.api;

import com.example.jeffmusic.model.LoginModel;
import com.example.jeffmusic.model.MusicModel;
import okhttp3.ResponseBody;
import poetry.jianjia.Call;
import poetry.jianjia.http.*;

import java.util.List;
import java.util.Map;

public interface MusicApi {

    @GET("music/all")
    Call<List<MusicModel>> getSongs(@Headers("Authorization") String token,@QueryMap Map<String, String> map);

}
