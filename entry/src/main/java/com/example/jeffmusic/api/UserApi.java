package com.example.jeffmusic.api;

import okhttp3.ResponseBody;
import poetry.jianjia.Call;
import poetry.jianjia.http.FieldMap;
import poetry.jianjia.http.FormUrlEncoded;
import poetry.jianjia.http.POST;

import java.util.Map;

public interface UserApi {
    @FormUrlEncoded
    @POST("user/register")
    Call<ResponseBody> register(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("user/login")
    Call<ResponseBody> login(@FieldMap Map<String, String> map);
}
