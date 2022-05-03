package com.example.jeffmusic.provider;

import com.bumptech.glide.Glide;
import com.example.jeffmusic.MyApplication;
import com.example.jeffmusic.ResourceTable;
import com.example.jeffmusic.model.MusicModel;
import com.example.jeffmusic.player.MusicPlayer;
import ohos.aafwk.ability.fraction.Fraction;
import ohos.agp.components.*;
import ohos.app.Context;

import java.util.ArrayList;
import java.util.List;

public class MusicItemProvider extends BaseItemProvider {
    private List<MusicModel> mList = new ArrayList<>();
    private Context mFraction;

    public MusicItemProvider(Context fraction) {
        this.mFraction = fraction;
    }

    public void setData(List<MusicModel> list) {
        if (list == null) {
            return;
        }
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
        MusicModel data = mList.get(i);
        final Image image = cpt.findComponentById(ResourceTable.Id_music_item_imag);
        final Text name = cpt.findComponentById(ResourceTable.Id_music_item_name);
        final Text author = cpt.findComponentById(ResourceTable.Id_music_item_author);
        Glide.with(componentContainer.getContext()).load(data.getCoverUrl()).into(image);
        name.setText(data.getName());
        author.setText("");
        cpt.setClickedListener(component -> {
            MusicPlayer player = MyApplication.getInstance().getPlayer();
            player.setPlayList(mList, i);
            player.startPlaying();
        });
        return cpt;
    }
}
