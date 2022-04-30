package com.example.jeffmusic.ability.slice;

import com.example.jeffmusic.MyApplication;
import com.example.jeffmusic.ResourceTable;
import com.example.jeffmusic.ability.LoginAbility;
import com.example.jeffmusic.ability.SiginAblity;
import com.example.jeffmusic.model.LoginModel;
import com.example.jeffmusic.utils.PlayerUtils;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Component;
import ohos.agp.components.Text;
import ohos.agp.components.TextField;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.window.dialog.ToastDialog;
import okhttp3.ResponseBody;
import poetry.jianjia.Call;
import poetry.jianjia.Callback;
import poetry.jianjia.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginAbilitySlice extends AbilitySlice {
    private boolean buttonClickable;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_login);
        initView();
    }

    private void initView() {
        findComponentById(ResourceTable.Id_login_change_text).setClickedListener(component -> {
            startSiginAbility();
            terminateAbility();
        });
        findComponentById(ResourceTable.Id_exit_button).setClickedListener(component -> {
            terminateAbility();
        });
        TextField nickNameText = findComponentById(ResourceTable.Id_nick_name_text);
        TextField passwordText = findComponentById(ResourceTable.Id_password_text);
        Text button = findComponentById(ResourceTable.Id_login_button);
        nickNameText.addTextObserver((s, i, i1, i2) -> {
            if (!PlayerUtils.isEmptyString(s) && !PlayerUtils.isEmptyString(passwordText.getText())) {
                button.setBackground(new ShapeElement(getAbility(), ResourceTable.Graphic_background_login_button_clickable));
                buttonClickable = true;
            } else {
                button.setBackground(new ShapeElement(getAbility(), ResourceTable.Graphic_background_login_button));
                buttonClickable = false;
            }
        });
        passwordText.addTextObserver((s, i, i1, i2) -> {
            if (!PlayerUtils.isEmptyString(s) && !PlayerUtils.isEmptyString(nickNameText.getText())) {
                button.setBackground(new ShapeElement(getAbility(), ResourceTable.Graphic_background_login_button_clickable));
                buttonClickable = true;
            } else {
                button.setBackground(new ShapeElement(getAbility(), ResourceTable.Graphic_background_login_button));
                buttonClickable = false;
            }
        });
        button.setClickedListener(component -> {
            if (buttonClickable) {
                Map<String, String> paramsMap = new HashMap<>();
                paramsMap.put("name", nickNameText.getText());
                paramsMap.put("password", passwordText.getText());
                MyApplication.getInstance().getUserApi().login(paramsMap).enqueue(new Callback<LoginModel>() {
                    @Override
                    public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                        LoginModel data = response.body();
                        MyApplication.getInstance().setToken(data.token);
                        MyApplication.getInstance().setUser(data.user);
                        new ToastDialog(getContext())
                                .setText("登陆成功！")
                                .show();
                    }

                    @Override
                    public void onFailure(Call<LoginModel> call, Throwable throwable) {
                        new ToastDialog(getContext())
                                .setText("登陆失败！")
                                .show();
                    }
                });
            }
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
