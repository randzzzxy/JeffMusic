package com.example.jeffmusic.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserModel implements Serializable {
    @SerializedName("union_id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("avatar_url")
    private String imageUrl;

    public UserModel(int id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public UserModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
