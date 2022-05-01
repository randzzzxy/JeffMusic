package com.example.jeffmusic;

import com.example.jeffmusic.model.UserModel;

public interface UserCallback {
    void onCurrentUserChanged(UserModel user);
}
