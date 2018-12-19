package com.dompine.himitsu.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Comment implements Serializable{


    private static final long serialVersionUID = 4626198671805947205L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;

    private long userId;

    private long postId;

    @Column(length = 600)
    private String comments;

    @CreatedDate
    private long createTime; //创建时间
}
