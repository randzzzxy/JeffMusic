package com.example.jeffmusic.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Collect implements Serializable {
    @SerializedName("play_list_id")
    public int playlistId;

    @SerializedName("song_id")
    public int songId;

    public Collect(int playlistId, int songId) {
        this.playlistId = playlistId;
        this.songId = songId;
    }
}
