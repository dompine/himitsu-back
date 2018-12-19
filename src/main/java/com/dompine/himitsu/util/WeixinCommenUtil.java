package com.dompine.himitsu.util;

/**
 * 微信公用操作的类，获取accessToken
 */

import com.dompine.himitsu.dao.AccessTokenDao;
import com.dompine.himitsu.entity.AccessToken;
import com.dompine.himitsu.entity.User;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;



@Component
public class WeixinCommenUtil {
    @Autowired
    AccessTokenDao accessTokenDao;
    @Value("${modelId}")
    String modelId;


    public AccessToken getToken(String appid, String appsecret){
        // 获取access_token的接口地址（GET） 限2000（次/天）
        String url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

        AccessToken token=null;
        //访问微信服务器的地址
        String requestUrl=url.replace("APPID", appid).replace("APPSECRET", appsecret);
        //HttpRequestUtil httpRequestUtil=new HttpRequestUtil();

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(requestUrl, String.class);
        Gson gson = new Gson();
        Map map = gson.fromJson(response, Map.class);
        System.out.println(map.get("access_token"));
        //判断json是否为空
        if (map!=null){

            try{
                token= new AccessToken();
                //将获取的access_token放入accessToken对象中
                token.setAccess_token((String)map.get("access_token"));
                //将获取的expires_in时间放入accessToken对象中
                token.setExpires_in((Double)map.get("expires_in"));
            }
            catch (Exception e){
                token=null;
                e.printStackTrace();
                System.out.println("系统出错了！");
            }
        }else {
            token=null;
            // 获取token失败
        }
        return token;

    }

    public void sendTMSG (String userOpenid,String formId,String contents){
        String url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=ACCESS_TOKEN";

        String acces_token = accessTokenDao.findById((long)1).get().getAccess_token();
        String SENDTMSGUrl = url.replace("ACCESS_TOKEN", acces_token);
        Map sendMap = new HashMap();
        sendMap.put("touser",userOpenid);
        sendMap.put("template_id",modelId);
        sendMap.put("page","index");
        sendMap.put("form_id",formId);
        Map data = new HashMap();
        Map keyword1 = new HashMap();
        keyword1.put("value",contents);
        data.put("keyword1",keyword1);
        Map keyword2 = new HashMap();
        keyword2.put("value","请打开小程序查看最新回复");
        data.put("keyword2",keyword2);
        sendMap.put("data",data);
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.postForObject(SENDTMSGUrl,(Object)sendMap,String.class);
        Gson gson = new Gson();
        Map map = gson.fromJson(response, Map.class);
        if(map !=null) {
            System.out.println(map.get("errmsg"));
            System.out.println(map.get("template_id"));
        }
    }

}
