package com.example.jeffmusic.ability.slice;

import com.example.jeffmusic.MyApplication;
import com.example.jeffmusic.ResourceTable;
import com.example.jeffmusic.model.Comment;
import com.example.jeffmusic.model.MusicModel;
import com.example.jeffmusic.model.Reply;
import com.example.jeffmusic.procotol.CommentClickListener;
import com.example.jeffmusic.procotol.ReplyClickListener;
import com.example.jeffmusic.provider.CommentItemProvider;
import com.example.jeffmusic.provider.MusicItemProvider;
import com.example.jeffmusic.provider.ReplyItemProvider;
import com.example.jeffmusic.utils.PlayerUtils;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.agp.window.dialog.CommonDialog;
import ohos.agp.window.dialog.ToastDialog;
import ohos.app.Context;
import okhttp3.ResponseBody;
import poetry.jianjia.Call;
import poetry.jianjia.Callback;
import poetry.jianjia.Response;

import java.util.List;

public class CommentDetailAbilitySlice extends AbilitySlice {
    private MusicModel mData;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_comment_detail);
        mData = MyApplication.getInstance().getPlayer().getCurrentSongInfo();
        initView();
    }

    private void initView() {
        findComponentById(ResourceTable.Id_back_button).setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                terminateAbility();
            }
        });
        Text musicNameText = findComponentById(ResourceTable.Id_music_name);
        musicNameText.setText(mData.getName());
        ListContainer listContainer = findComponentById(ResourceTable.Id_comment_list_container);
        TextField textField = findComponentById(ResourceTable.Id_comment_input_text_field);
        Image sendImage = findComponentById(ResourceTable.Id_send_comment_button);
        CommentItemProvider provider = new CommentItemProvider(getAbility(), new CommentClickListener() {
            @Override
            public void onClick(Comment comment) {
                //点击评论后
            }

            @Override
            public void onClickToReply(Comment comment) {
                //点击回复
                MyApplication.getInstance().getCommentApi().getReplies(MyApplication.getInstance().getToken(),
                        MyApplication.getInstance().getUser().getId()).enqueue(new Callback<List<Reply>>() {
                    @Override
                    public void onResponse(Call<List<Reply>> call, Response<List<Reply>> response) {
                        if (response.code() == 200) {
                            showReplyDialog(response.body(), comment);
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<List<Reply>> call, Throwable throwable) {

                    }
                });
            }
        });
        listContainer.setItemProvider(provider);
        sendImage.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                if (!PlayerUtils.isEmptyString(textField.getText())) {
                    MyApplication.getInstance().getCommentApi().postComment(MyApplication.getInstance().getToken(),
                            new Comment(textField.getText(), mData.id)).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.code() == 200) {
                                new ToastDialog(getContext())
                                        .setText("评论成功")
                                        .show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable throwable) {

                        }
                    });
                }
            }
        });
        MyApplication.getInstance().getCommentApi().getComments(MyApplication.getInstance().getToken(),
                mData.id).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.code() == 200) {
                    provider.setData(response.body());
                } else {

                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable throwable) {

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

    private void showReplyDialog(List<Reply> data, Comment comment) {
        CommonDialog dialog = new CommonDialog(getContext());
        ComponentContainer container = (ComponentContainer) LayoutScatter.getInstance(getContext()).parse(ResourceTable.Layout_dialog_reply_detail
                , null, false);
        dialog.setContentCustomComponent(container);
        Text replyText = container.findComponentById(ResourceTable.Id_reply_input_text_field);
        Image sendButton = container.findComponentById(ResourceTable.Id_send_comment_button);
        ListContainer listComponent = container.findComponentById(ResourceTable.Id_comment_list_container);
        ReplyItemProvider replyItemProvider = new ReplyItemProvider(getAbility(), new ReplyClickListener() {
            @Override
            public void onClick(Reply comment) {

            }
        });
        listComponent.setItemProvider(replyItemProvider);
        replyItemProvider.setData(data);
        sendButton.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                if (!PlayerUtils.isEmptyString(replyText.getText())) {
                    MyApplication.getInstance().getCommentApi().postReply(MyApplication.getInstance().getToken(),
                            new Reply(replyText.getText(), comment.id)).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.code() == 200) {
                                new ToastDialog(getContext())
                                        .setText("回复成功")
                                        .show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable throwable) {

                        }
                    });
                }
            }
        });
        dialog.show();
    }
}
