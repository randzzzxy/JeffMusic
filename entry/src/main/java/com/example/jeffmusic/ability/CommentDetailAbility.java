package com.example.jeffmusic.ability;

import com.example.jeffmusic.ability.slice.CommentDetailAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class CommentDetailAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(CommentDetailAbilitySlice.class.getName());
    }
}
