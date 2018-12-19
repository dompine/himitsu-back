package com.dompine.himitsu.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Post implements Serializable {

    private static final long serialVersionUID = 3539721131716476783L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long postId;  //帖子id

    private long userId;  //用户id

    @CreatedDate
    private long createTime; //创建时间

    @Column(length = 600)
    private String contents;  //帖子内容

    private String formId; //坑爹的formId 七天有效
}
