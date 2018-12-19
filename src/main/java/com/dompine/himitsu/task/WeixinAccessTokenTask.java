package com.dompine.himitsu.task;

/**
 * 微信的accessToken有效时间为两个小时，调用微信接口很多都要用到accessToken所以需要将accessToken保存（保存到内存中或者保存到数据库中）这里使用保存到内存中的方式实现
 */

import com.dompine.himitsu.dao.AccessTokenDao;
import com.dompine.himitsu.entity.AccessToken;
import com.dompine.himitsu.util.WeixinCommenUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class WeixinAccessTokenTask {

    @Autowired
    private WeixinCommenUtil weixinCommenUtil;
    @Autowired
    private AccessTokenDao accessTokenDao;
    @Value("${appid}")
    String appid;
    @Value("${secret}")
    String secret;

    // 第一次延迟1秒执行，当执行完后7100秒再执行
    @Scheduled(initialDelay = 1000, fixedDelay = 7000 * 1000)
    public void getWeixinAccessToken() {

        try {
            AccessToken token = weixinCommenUtil.getToken(appid, secret);
            token.setAccestokenId(1);
            accessTokenDao.save(token);
        } catch (Exception e) {

            e.printStackTrace();
            this.getWeixinAccessToken();
            // 此处可能陷入死循环
        }

    }

}
