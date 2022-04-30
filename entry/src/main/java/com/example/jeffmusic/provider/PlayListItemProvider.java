package com.example.jeffmusic.provider;

import com.bumptech.glide.Glide;
import com.example.jeffmusic.ResourceTable;
import com.example.jeffmusic.manager.MainPageFractionManger;
import com.example.jeffmusic.model.MusicModel;
import com.example.jeffmusic.model.PlayList;
import ohos.aafwk.ability.fraction.Fraction;
import ohos.agp.components.*;

import java.util.ArrayList;
import java.util.List;

public class PlayListItemProvider extends BaseItemProvider {
    private List<PlayList> mList = new ArrayList<>();
    private Fraction mFraction;
    private MainPageFractionManger mMainPageFractionManger;

    public PlayListItemProvider(Fraction fraction, MainPageFractionManger mainPageFractionManger) {
        this.mFraction = fraction;
        mMainPageFractionManger = mainPageFractionManger;
    }

    public void setData(List<PlayList> list) {
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
            cpt = LayoutScatter.getInstance(mFraction).parse(ResourceTable.Layout_item_playlist, null, false);
        } else {
            cpt = convertComponent;
        }
        PlayList data = mList.get(i);
        final Text name = cpt.findComponentById(ResourceTable.Id_item_playlist_name);
        name.setText(data.name);
        componentContainer.setClickedListener(component -> {
            mFraction.getFractionAbility().getIntent().setParam("play_list_id", data.id);
            mMainPageFractionManger.setData(MainPageFractionManger.TAB_PLAYLIST_DETAIL);
        });
        return cpt;
    }
}
