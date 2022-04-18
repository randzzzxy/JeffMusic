package com.example.jeffmusic.model;

import java.io.Serializable;

public class MusicModel implements Serializable {
    private String name;
    private UserModel author;
    private String songUrl;
    private String coverUrl;
    private String lyricsUrl;

    public MusicModel(String name, UserModel author, String songUrl, String coverUrl, String lyricsUrl) {
        this.name = name;
        this.author = author;
        this.songUrl = songUrl;
        this.coverUrl = coverUrl;
        this.lyricsUrl = lyricsUrl;
    }

    public MusicModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserModel getAuthor() {
        return author;
    }

    public void setAuthor(UserModel author) {
        this.author = author;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getLyricsUrl() {
        return lyricsUrl;
    }

    public void setLyricsUrl(String lyricsUrl) {
        this.lyricsUrl = lyricsUrl;
    }
}
