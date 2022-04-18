package com.example.jeffmusic.fraction;

import com.example.jeffmusic.ResourceTable;
import com.example.jeffmusic.model.MusicModel;
import com.example.jeffmusic.provider.MusicItemProvider;
import ohos.aafwk.ability.fraction.Fraction;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.ListContainer;

import java.util.ArrayList;
import java.util.List;

public class MusicFraction extends Fraction {
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
        List<MusicModel> list = getData();
        MusicItemProvider musicItemProvider = new MusicItemProvider(this);
        listContainer.setItemProvider(musicItemProvider);
        musicItemProvider.setData(list);
    }
    private ArrayList<MusicModel> getData() {
        ArrayList<MusicModel> list = new ArrayList<>();
        for (int i = 0; i <= 8; i++) {
            list.add(new MusicModel("Item" + i, null, null, null, null));
        }
        return list;
    }
}
