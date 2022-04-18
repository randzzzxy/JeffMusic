package com.example.jeffmusic.fraction;

import com.example.jeffmusic.ResourceTable;
import ohos.aafwk.ability.fraction.Fraction;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.LayoutScatter;

public class FavoriteFraction extends Fraction {
    @Override
    protected Component onComponentAttached(LayoutScatter scatter, ComponentContainer container, Intent intent) {
        return scatter.parse(ResourceTable.Layout_fraction_favorite, container, false);
    }

    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
