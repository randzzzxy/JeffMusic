package com.example.jeffmusic.fraction;

import com.example.jeffmusic.MyApplication;
import com.example.jeffmusic.ResourceTable;
import com.example.jeffmusic.manager.MainPageFractionManger;
import com.example.jeffmusic.model.PlayList;
import com.example.jeffmusic.provider.PlayListItemProvider;
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

import java.util.List;

public class PlayListFraction extends Fraction {
    private PlayListItemProvider mPlayListItemProvider;
    private MainPageFractionManger mMainPageFractionManger;

    public PlayListFraction(MainPageFractionManger mainPageFractionManger) {
        mMainPageFractionManger = mainPageFractionManger;
    }

    @Override
    protected Component onComponentAttached(LayoutScatter scatter, ComponentContainer container, Intent intent) {
        return scatter.parse(ResourceTable.Layout_fraction_playlist, container, false);
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
        mPlayListItemProvider = new PlayListItemProvider(this, mMainPageFractionManger);
        listContainer.setItemProvider(mPlayListItemProvider);
        fetchData();
    }
    private void fetchData() {
        MyApplication.getInstance().getMusicApi().getPlayList(MyApplication.getInstance().getToken()).enqueue(new Callback<List<PlayList>>() {
            @Override
            public void onResponse(Call<List<PlayList>> call, Response<List<PlayList>> response) {
                mPlayListItemProvider.setData(response.body());
            }

            @Override
            public void onFailure(Call<List<PlayList>> call, Throwable throwable) {
                new ToastDialog(getContext())
                        .setText("加载歌单失败")
                        .show();
            }
        });
    }
}
