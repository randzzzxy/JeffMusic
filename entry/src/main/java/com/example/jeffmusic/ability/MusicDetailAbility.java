package com.example.jeffmusic.ability;

import com.example.jeffmusic.ability.slice.MusicDetailAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class MusicDetailAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(MusicDetailAbilitySlice.class.getName());
    }
}
