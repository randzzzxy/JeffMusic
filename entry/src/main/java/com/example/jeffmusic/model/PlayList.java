package com.example.jeffmusic.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PlayList implements Serializable {
    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

}
