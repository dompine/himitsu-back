package com.dompine.himitsu.service;

import com.dompine.himitsu.dao.CommentDao;
import com.dompine.himitsu.dao.PostDao;
import com.dompine.himitsu.entity.Comment;
import com.dompine.himitsu.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentDao commentDao;

    @Autowired
    private PostDao postDao;

    @Override
    public void save(Comment comment) {
        commentDao.save(comment);
    }

    @Override
    public List<Post> getCommentHistory(long userId) {
        List<Comment> comments = commentDao.findByUserId(userId);
        List<Long> postIds = new ArrayList<Long>();
        List<Post> posts = new ArrayList<Post>();
        for (Comment c : comments){
            posts.add(postDao.findById(c.getPostId()).get());
        }
        return posts;
    }
}
