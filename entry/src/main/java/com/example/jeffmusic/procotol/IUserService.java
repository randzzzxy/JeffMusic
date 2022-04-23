package com.example.jeffmusic.procotol;

import com.example.jeffmusic.model.UserModel;

public interface IUserService {
    //注册
    UserModel loginAccount(UserModel userModel);

    //当前用户信息
    UserModel currentUser();
}
