package com.example.jeffmusic.ability.slice;

import com.example.jeffmusic.MyApplication;
import com.example.jeffmusic.ResourceTable;
import com.example.jeffmusic.utils.PlayerUtils;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Text;
import ohos.agp.components.TextField;
import ohos.agp.components.element.Element;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.components.element.VectorElement;
import ohos.agp.window.dialog.ToastDialog;
import ohos.global.resource.NotExistException;
import ohos.global.resource.WrongTypeException;
import okhttp3.ResponseBody;
import poetry.jianjia.Call;
import poetry.jianjia.Callback;
import poetry.jianjia.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SiginAblitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_sigin);
        initView();
    }

    private void initView() {
        TextField nickNameText = findComponentById(ResourceTable.Id_nick_name_text);
        TextField passwordText = findComponentById(ResourceTable.Id_password_text);
        Text button = findComponentById(ResourceTable.Id_sigin_button);
        nickNameText.addTextObserver((s, i, i1, i2) -> {
            if (!PlayerUtils.isEmptyString(s) && !PlayerUtils.isEmptyString(passwordText.getText())) {
                button.setBackground(new ShapeElement(getAbility(), ResourceTable.Graphic_background_login_button_clickable));
            } else {
                button.setBackground(new ShapeElement(getAbility(), ResourceTable.Graphic_background_login_button));
            }
        });
        passwordText.addTextObserver((s, i, i1, i2) -> {
            if (!PlayerUtils.isEmptyString(s) && !PlayerUtils.isEmptyString(nickNameText.getText())) {
                button.setBackground(new ShapeElement(getAbility(), ResourceTable.Graphic_background_login_button_clickable));
            } else {
                button.setBackground(new ShapeElement(getAbility(), ResourceTable.Graphic_background_login_button));
            }
        });
        button.setClickedListener(component -> {
            String nickName = nickNameText.getText();
            String password = passwordText.getText();
            if (PlayerUtils.isEmptyString(nickName) || PlayerUtils.isEmptyString(password)) {
                new ToastDialog(getContext())
                        .setText("请检查用户名或密码是否为空！")
                        .show();
            } else {
                Map<String, String> paramsMap = new HashMap<>();
                paramsMap.put("name", nickName);
                paramsMap.put("password", password);
                MyApplication.getInstance().getUserApi().register(paramsMap).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        new ToastDialog(getContext())
                                .setText("注册成功！")
                                .show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                        new ToastDialog(getContext())
                                .setText("注册失败！")
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
}
