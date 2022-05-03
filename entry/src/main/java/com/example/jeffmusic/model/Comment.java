package com.example.jeffmusic.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Comment implements Serializable {
    @SerializedName("id")
    public int id;

    @SerializedName("comment")
    public String comment;

    @SerializedName("song_id")
    public int songId;

    @SerializedName("user_id")
    public int userId;

    public Comment(String comment, int songId) {
        this.comment = comment;
        this.songId = songId;
    }
}
