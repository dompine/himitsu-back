package com.dompine.himitsu.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class HasRead implements Serializable {

    private static final long serialVersionUID = 2671527524707823507L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long hasReadId;  //已读表id

    private long userId; //用户id

    private long postId; //帖子id
}
