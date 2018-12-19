package com.dompine.himitsu.dao;

import com.dompine.himitsu.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CommentDao extends JpaRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {
     List<Comment> findByUserId(long userId);

    List<Comment> findAllByPostIdOrderByCreateTimeDesc(long postId);
}
