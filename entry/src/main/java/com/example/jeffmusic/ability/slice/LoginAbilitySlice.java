package com.example.jeffmusic.ability.slice;

import com.example.jeffmusic.ResourceTable;
import com.example.jeffmusic.ability.LoginAbility;
import com.example.jeffmusic.ability.SiginAblity;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Component;

public class LoginAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_login);
        initView();
    }

    private void initView() {
        findComponentById(ResourceTable.Id_login_change_text).setClickedListener(component -> {
            startSiginAbility();
        });
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

    //进入注册页面
    private void startSiginAbility() {
        Intent intent = new Intent();
        Operation operation = new Intent.OperationBuilder().withDeviceId("")
                .withBundleName(getBundleName())
                .withAbilityName(SiginAblity.class)
                .build();
        intent.setOperation(operation);
        startAbility(intent);
    }
}
