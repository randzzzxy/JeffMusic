package com.example.jeffmusic.provider;

import com.example.jeffmusic.ResourceTable;
import com.example.jeffmusic.model.MusicModel;
import ohos.aafwk.ability.fraction.Fraction;
import ohos.agp.components.BaseItemProvider;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.LayoutScatter;

import java.util.ArrayList;
import java.util.List;

public class MusicItemProvider extends BaseItemProvider {
    private List<MusicModel> mList = new ArrayList<>();
    private Fraction mFraction;

    public MusicItemProvider(Fraction fraction) {
        this.mFraction = fraction;
    }

    public void setData(List<MusicModel> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataChanged();
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }
    @Override
    public Object getItem(int position) {
        if (mList != null && position >= 0 && position < mList.size()){
            return mList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Component getComponent(int i, Component convertComponent, ComponentContainer componentContainer) {
        final Component cpt;
        if (convertComponent == null) {
            cpt = LayoutScatter.getInstance(mFraction).parse(ResourceTable.Layout_item_music, null, false);
        } else {
            cpt = convertComponent;
        }
        return cpt;
    }
}
