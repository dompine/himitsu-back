package com.dompine.himitsu.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class TodayRead implements Serializable {

    private static final long serialVersionUID = -2892619263947906425L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long todayReadId;  //今日已读表id

    private long userId; //用户id

    private long postId; //帖子id
}
