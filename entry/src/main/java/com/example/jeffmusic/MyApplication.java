package com.example.jeffmusic;

import com.example.jeffmusic.api.MusicApi;
import com.example.jeffmusic.api.UserApi;
import com.example.jeffmusic.model.MusicModel;
import com.example.jeffmusic.model.UserModel;
import com.example.jeffmusic.player.MusicPlayer;
import com.example.jeffmusic.utils.PlayerUtils;
import ohos.aafwk.ability.AbilityPackage;
import ohos.app.Context;
import ohos.data.DatabaseHelper;
import ohos.data.preferences.Preferences;
import ohos.global.resource.ResourceManager;
import poerty.jianjian.converter.gson.GsonConverterFactory;
import poetry.jianjia.Call;
import poetry.jianjia.Callback;
import poetry.jianjia.JianJia;
import poetry.jianjia.Response;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends AbilityPackage {
    public static String RESOURCE_MUSIC_URL_PRE = "http://192.168.0.103:8080/file/song?name=";
    private static MyApplication instance;
    private JianJia mJianJia;
    private UserApi mUserApi;
    private UserModel mUser;
    private Preferences mPreferences;
    private MusicApi mMusicApi;
    private List<UserCallback> mUserObservers = new ArrayList<>();
    private MusicPlayer mPlayer = new MusicPlayer(this);

    public static MyApplication getInstance() {
        return instance;
    }

    public void setUser(UserModel user) {
        mUser = user;
        for (int i = 0; i < mUserObservers.size(); i++) {
            mUserObservers.get(i).onCurrentUserChanged(mUser);
        }
    }

    public MusicPlayer getPlayer() {
        return mPlayer;
    }

    public JianJia getJianJia() {
        return mJianJia;
    }

    public UserApi getUserApi() {
        return mUserApi;
    }

    public MusicApi getMusicApi() {
        return mMusicApi;
    }

    public String getToken() {
        return mPreferences.getString("token", "");
    }

    public ResourceManager getResourceManager() {
        return getContext().getResourceManager();
    }

    public void setToken(String token) {
        mPreferences.putString("token", token);
        mPreferences.flush();
    }

    @Override
    public void onInitialize() {
        super.onInitialize();
        instance = this;
        // 创建全局的蒹葭对象
        mJianJia = new JianJia.Builder()
                .baseUrl("http://192.168.0.103:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mUserApi = mJianJia.create(UserApi.class);
        mMusicApi = mJianJia.create(MusicApi.class);
        Context context = getContext(); // 数据文件存储路径：/data/data/{PackageName}/{AbilityName}/preferences。
        // Context context = getApplicationContext(); // 数据文件存储路径：/data/data/{PackageName}/preferences。
        DatabaseHelper databaseHelper = new DatabaseHelper(context); // context入参类型为ohos.app.Context。
        String fileName = "token"; // fileName表示文件名，其取值不能为空，也不能包含路径，默认存储目录可以通过context.getPreferencesDir()获取。
        mPreferences = databaseHelper.getPreferences(fileName);
        if (!PlayerUtils.isEmptyString(getToken())) {
            mMusicApi.getUserByToken(getToken()).enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                    if (response.code() == 200) {
                        setUser(response.body());
                    }
                }

                @Override
                public void onFailure(Call<UserModel> call, Throwable throwable) {

                }
            });
        }
    }

    public void observeUser(UserCallback callback){
        mUserObservers.add(callback);
        callback.onCurrentUserChanged(mUser);
    }

    public UserModel getUser() {
        return mUser;
    }
}
