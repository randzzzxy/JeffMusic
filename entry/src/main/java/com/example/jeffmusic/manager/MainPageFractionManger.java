package com.example.jeffmusic.manager;

import java.util.ArrayList;
import java.util.List;

public class MainPageFractionManger {
    public final static int TAB_MUSIC = 0;
    public final static int TAB_PLAYLIST = 1;
    public static final int TAB_PLAYLIST_DETAIL = 2;
    private int mCurrentTab;
    private List<MainPageFractionChangeCallback> observers = new ArrayList<>();

    public void observe(MainPageFractionChangeCallback callback) {
        observers.add(callback);
        callback.onFractionChange(mCurrentTab);
    }

    public void setData(int currentTab) {
        mCurrentTab = currentTab;
        notifyDataChanged();
    }

    private void notifyDataChanged() {
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).onFractionChange(mCurrentTab);
        }
    }

}
