package com.example.jeffmusic.utils;

import java.io.Serializable;

public class Result{
    private Serializable mData;
    private int mStatusCode;

    public Result(Serializable mData, int mStatusCode) {
        this.mData = mData;
        this.mStatusCode = mStatusCode;
    }

    public Serializable getmData() {
        return mData;
    }

    public int getmStatusCode() {
        return mStatusCode;
    }
}
