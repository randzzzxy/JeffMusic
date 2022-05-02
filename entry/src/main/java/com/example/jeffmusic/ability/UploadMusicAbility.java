package com.example.jeffmusic.ability;

import com.example.jeffmusic.ability.slice.UploadMusicAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class UploadMusicAbility extends Ability {
    private UploadMusicAbilitySlice slice;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(UploadMusicAbilitySlice.class.getName());
    }

    public void setSlice(UploadMusicAbilitySlice slice) {
        this.slice = slice;
    }

    @Override
    protected void onAbilityResult(int requestCode, int resultCode, Intent resultData) {
        super.onAbilityResult(requestCode, resultCode, resultData);
        slice.onAbilityResultManual(requestCode,resultCode,resultData);
    }
}
