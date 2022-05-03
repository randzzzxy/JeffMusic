package com.example.jeffmusic.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Reply implements Serializable {
    @SerializedName("id")
    public int id;

    @SerializedName("comment")
    public String comment;

    @SerializedName("comment_id")
    public int commentId;

    @SerializedName("user_id")
    public int userId;

    public Reply(String comment, int commentId) {
        this.comment = comment;
        this.commentId = commentId;
        this.userId = userId;
    }
}
