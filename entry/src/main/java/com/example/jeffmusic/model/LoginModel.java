package com.example.jeffmusic.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginModel implements Serializable {
    @SerializedName("token")
    public String token;

    @SerializedName("user")
    public UserModel user;
}
