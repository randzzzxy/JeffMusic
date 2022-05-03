package com.example.jeffmusic.provider;

import com.example.jeffmusic.ResourceTable;
import com.example.jeffmusic.model.Comment;
import com.example.jeffmusic.model.Reply;
import com.example.jeffmusic.procotol.CommentClickListener;
import com.example.jeffmusic.procotol.ReplyClickListener;
import ohos.agp.components.*;
import ohos.app.Context;

import java.util.ArrayList;
import java.util.List;

public class ReplyItemProvider extends BaseItemProvider {
    private List<Reply> mList = new ArrayList<>();
    private Context mContext;
    private ReplyClickListener mListener;

    public ReplyItemProvider(Context context, ReplyClickListener listener) {
        this.mContext = context;
        this.mListener = listener;
    }

    public void setData(List<Reply> list) {
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
        Reply data = mList.get(i);
        final Text comment = cpt.findComponentById(ResourceTable.Id_comment_content);
        comment.setText(data.comment);
        componentContainer.setClickedListener(component -> {
            mListener.onClick(data);
        });
        return cpt;
    }
}
