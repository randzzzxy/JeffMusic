package com.example.jeffmusic.api;

import com.example.jeffmusic.model.*;
import okhttp3.MultipartBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import poetry.jianjia.Call;
import poetry.jianjia.http.*;

import java.util.List;
import java.util.Map;

public interface CommentApi {

    @GET("comment/comment")
    Call<List<Comment>> getComments(@Header("Authorization") String token, @Query("song_id") int songId);

    @POST("comment/comment")
    Call<ResponseBody> postComment(@Header("Authorization") String token, @Body Comment comment);

    @GET("comment/reply")
    Call<List<Reply>> getReplies(@Header("Authorization") String token, @Query("comment_id") int songId);

    @POST("comment/reply")
    Call<ResponseBody> postReply(@Header("Authorization") String token, @Body Reply reply);

}
