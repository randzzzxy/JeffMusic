package com.example.jeffmusic.ability.slice;

import com.example.jeffmusic.MyApplication;
import com.example.jeffmusic.ResourceTable;
import com.example.jeffmusic.ability.UploadMusicAbility;
import com.example.jeffmusic.utils.PlayerUtils;
import com.kbeanie.multipicker.api.Constants;
import com.kbeanie.multipicker.api.entity.ChoosenFile;
import com.kbeanie.multipicker.core.PickManager;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Component;
import ohos.agp.components.Text;
import ohos.agp.components.TextField;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.window.dialog.ToastDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import poetry.jianjia.Call;
import poetry.jianjia.Callback;
import poetry.jianjia.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UploadMusicAbilitySlice extends AbilitySlice {
    private boolean isEmptyName = true;
    private boolean isEmptyMusicFile = true;
    private boolean isEmptyCoverFile = true;
    private boolean isEmptyLyricsFile = true;
    private int choose = -1;
    private static final int RESULT_CODE_MUSIC = 0;
    private static final int RESULT_CODE_COVER = 1;
    private static final int RESULT_CODE_LYRICS = 2;
    private String mMusicFilePath = "";
    private String mCoverFilePath = "";
    private String mLyricsFilePath = "";
    private Text mButton;
    private TextField mName;
    Text musicFile;
    Text coverFile;
    Text lyricsFile;
    PickManager mPickManager;


    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_upload_music);
        ((UploadMusicAbility)getAbility()).setSlice(this);
        mPickManager = PickManager.getInstance(getAbility());
        initView();
    }

    private void initView() {
        mName = findComponentById(ResourceTable.Id_upload_music_name_text_field);
        musicFile = findComponentById(ResourceTable.Id_upload_music_file_text);
        coverFile = findComponentById(ResourceTable.Id_upload_cover_file_text);
        lyricsFile = findComponentById(ResourceTable.Id_upload_lysics_file_text);
        mButton = findComponentById(ResourceTable.Id_upload_button);
        mName.addTextObserver((s, i, i1, i2) -> {
            if (PlayerUtils.isEmptyString(s)) {
                isEmptyName = true;
            } else {
                isEmptyName = false;
            }
            updateButtonColor();
        });
        musicFile.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                mPickManager// ability
                        .setMultiple(false)//关闭多选
                        .pick(Constants.SINGLE_AUDIO);
                choose = RESULT_CODE_MUSIC;
            }
        });
        coverFile.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                mPickManager// ability
                        .setMultiple(false)//关闭多选
                        .pick(Constants.SINGLE_IMAGE);
                choose = RESULT_CODE_COVER;
            }
        });
        lyricsFile.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                mPickManager// ability
                        .setMultiple(false)//关闭多选
                        .pick(Constants.SINGLE_FILE);
                choose = RESULT_CODE_LYRICS;
            }
        });
        mButton.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                if (!isEmptyName && !isEmptyMusicFile && !isEmptyCoverFile && !isEmptyLyricsFile) {
                    File musicFile = new File(mMusicFilePath);
                    File coverFile = new File(mCoverFilePath);
                    File lyricsFile = new File(mLyricsFilePath);
                    List<MultipartBody.Part> parts = new ArrayList<>();
                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), musicFile);
                    MultipartBody.Part part = MultipartBody.Part.createFormData("song", musicFile.getName(), requestBody);
                    parts.add(part);
                    RequestBody requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), coverFile);
                    MultipartBody.Part part1 = MultipartBody.Part.createFormData("cover", coverFile.getName(), requestBody1);
                    parts.add(part1);
                    RequestBody requestBody2 = RequestBody.create(MediaType.parse("multipart/form-data"), lyricsFile);
                    MultipartBody.Part part2 = MultipartBody.Part.createFormData("lyrics", lyricsFile.getName(), requestBody2);
                    parts.add(part2);
                    MyApplication.getInstance().getMusicApi().uploadMusic(MyApplication.getInstance().getToken(),
                            mName.getText(),
                            parts).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.code() == 200 ){
                                new ToastDialog(getContext())
                                        .setText("上传成功！")
                                        .show();
                                terminateAbility();
                            } else {
                                new ToastDialog(getContext())
                                        .setText("上传失败！")
                                        .show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                            new ToastDialog(getContext())
                                    .setText("上传失败！")
                                    .show();
                        }
                    });
                }
            }
        });
    }

    public void onAbilityResultManual(int requestCode, int resultCode, Intent resultData) {
        super.onAbilityResult(requestCode, resultCode, resultData);
        List<ChoosenFile> file = mPickManager.onAbilityResult(requestCode, resultCode, resultData);
        //file为选择文件返回的ChoosenFile对象
        if (file != null && file.size() != 0 && choose != -1) {
            switch (choose) {
                case RESULT_CODE_MUSIC:
                    isEmptyMusicFile = false;
                    mMusicFilePath = file.get(0).getFilePath();
                    musicFile.setText(file.get(0).getDisplayName());
                    break;
                case RESULT_CODE_COVER:
                    isEmptyCoverFile = false;
                    mCoverFilePath = file.get(0).getFilePath();
                    coverFile.setText(file.get(0).getDisplayName());
                    break;
                case RESULT_CODE_LYRICS:
                    isEmptyLyricsFile = false;
                    mLyricsFilePath = file.get(0).getFilePath();
                    lyricsFile.setText(file.get(0).getDisplayName());
                    break;
            }
            choose = -1;
        }
        updateButtonColor();
    }

    private void updateButtonColor() {
        if (!isEmptyName && !isEmptyMusicFile && !isEmptyCoverFile && !isEmptyLyricsFile) {
            mButton.setBackground(new ShapeElement(getAbility(), ResourceTable.Graphic_background_login_button_clickable));
        } else {
            mButton.setBackground(new ShapeElement(getAbility(), ResourceTable.Graphic_background_login_button));
        }
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
