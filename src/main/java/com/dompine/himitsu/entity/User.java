package com.dompine.himitsu.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class User implements Serializable {


    private static final long serialVersionUID = 3012450314524422875L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;  //用户id

    private String nickName;   //姓名

    private Integer gender = 0;  //性别：1为男性，2为女性

    private Integer tickets = 0;  //门票、货币

    private String avatarUrl; //头像

    private Integer sendTimes = 1; //剩余发送次数

    private String userOpenid;  //用户openid

    private Integer times = 3; //剩余次数

    private Integer commentTimes = 1; //回复次数

    private Integer maxTickets = 10; //每日最大得到票的次数

    private Integer hasNew = 0; //  0为无新回复，1为有新回复

    private String formId; //formId
}
