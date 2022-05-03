package com.example.jeffmusic.provider;

import com.example.jeffmusic.ResourceTable;
import com.example.jeffmusic.manager.MainPageFractionManger;
import com.example.jeffmusic.model.Comment;
import com.example.jeffmusic.model.PlayList;
import com.example.jeffmusic.procotol.CommentClickListener;
import ohos.aafwk.ability.fraction.Fraction;
import ohos.agp.components.*;
import ohos.app.Context;

import java.util.ArrayList;
import java.util.List;

public class CommentItemProvider extends BaseItemProvider {
    private List<Comment> mList = new ArrayList<>();
    private Context mContext;
    private CommentClickListener mListener;

    public CommentItemProvider(Context context, CommentClickListener listener) {
        this.mContext = context;
        this.mListener = listener;
    }

    public void setData(List<Comment> list) {
        if (list == null) {
            return;
        }
        mList.clear();
        mList.addAll(list);
        notifyDataChanged();
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }
    @Override
    public Object getItem(int position) {
        if (mList != null && position >= 0 && position < mList.size()){
            return mList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Component getComponent(int i, Component convertComponent, ComponentContainer componentContainer) {
        final Component cpt;
        if (convertComponent == null) {
            cpt = LayoutScatter.getInstance(mContext).parse(ResourceTable.Layout_item_comment, null, false);
        } else {
            cpt = convertComponent;
        }
        Comment data = mList.get(i);
        final Text comment = cpt.findComponentById(ResourceTable.Id_comment_content);
        final Text replyButton = cpt.findComponentById(ResourceTable.Id_replay_button);
        comment.setText(data.comment);
        replyButton.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                mListener.onClickToReply(data);
            }
        });
        componentContainer.setClickedListener(component -> {
            mListener.onClick(data);
        });
        return cpt;
    }
}
