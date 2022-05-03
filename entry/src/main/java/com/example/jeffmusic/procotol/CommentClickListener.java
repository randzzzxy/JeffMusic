package com.example.jeffmusic.procotol;

import com.example.jeffmusic.model.Comment;

public interface CommentClickListener {
    void onClick(Comment comment);

    void onClickToReply(Comment comment);
 }
