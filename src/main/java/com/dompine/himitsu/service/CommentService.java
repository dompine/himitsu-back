package com.dompine.himitsu.service;

import com.dompine.himitsu.entity.Comment;
import com.dompine.himitsu.entity.Post;

import java.util.List;

public interface CommentService {
    void save(Comment comment);

    List<Post> getCommentHistory(long userId);
}
