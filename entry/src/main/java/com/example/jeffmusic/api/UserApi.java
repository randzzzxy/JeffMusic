package com.example.jeffmusic.api;

import com.example.jeffmusic.model.LoginModel;
import okhttp3.ResponseBody;
import poetry.jianjia.Call;
import poetry.jianjia.http.Body;
import poetry.jianjia.http.FieldMap;
import poetry.jianjia.http.FormUrlEncoded;
import poetry.jianjia.http.POST;

import java.util.Map;

public interface UserApi {
    @POST("user/register")
    Call<ResponseBody> register(@Body Map<String, String> map);

    @POST("user/login")
    Call<LoginModel> login(@Body Map<String, String> map);
}
