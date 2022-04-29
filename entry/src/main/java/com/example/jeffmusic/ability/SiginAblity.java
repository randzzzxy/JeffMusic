package com.example.jeffmusic.ability;

import com.example.jeffmusic.ability.slice.SiginAblitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class SiginAblity extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(SiginAblitySlice.class.getName());
    }
}
