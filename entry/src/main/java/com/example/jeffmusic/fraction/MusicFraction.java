package com.example.jeffmusic.fraction;

import com.example.jeffmusic.MyApplication;
import com.example.jeffmusic.ResourceTable;
import com.example.jeffmusic.model.MusicModel;
import com.example.jeffmusic.provider.MusicItemProvider;
import ohos.aafwk.ability.fraction.Fraction;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.ListContainer;
import ohos.agp.window.dialog.ToastDialog;
import poetry.jianjia.Call;
import poetry.jianjia.Callback;
import poetry.jianjia.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MusicFraction extends Fraction {
    private int pageSize = 10;
    private int pageNumber = 0;
    private MusicItemProvider mMusicItemProvider;

    @Override
    protected Component onComponentAttached(LayoutScatter scatter, ComponentContainer container, Intent intent) {
        return scatter.parse(ResourceTable.Layout_fraction_music, container, false);
    }

    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        initListContainer();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void initListContainer() {
        ListContainer listContainer = getFractionAbility().findComponentById(ResourceTable.Id_list_container);
        mMusicItemProvider = new MusicItemProvider(this);
        listContainer.setItemProvider(mMusicItemProvider);
        fetchData();
    }
    private void fetchData() {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("pageNumber", pageNumber + "");
        paramsMap.put("pageSize", pageSize + "");
        MyApplication.getInstance().getMusicApi().getSongs(MyApplication.getInstance().getToken()
                ,paramsMap).enqueue(new Callback<List<MusicModel>>() {
            @Override
            public void onResponse(Call<List<MusicModel>> call, Response<List<MusicModel>> response) {
                pageNumber++;
                mMusicItemProvider.setData(response.body());
            }

            @Override
            public void onFailure(Call<List<MusicModel>> call, Throwable throwable) {
                new ToastDialog(getContext())
                        .setText("加载歌曲失败")
                        .show();
            }
        });
    }
}
