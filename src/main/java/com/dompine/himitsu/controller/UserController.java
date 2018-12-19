package com.dompine.himitsu.controller;

import com.dompine.himitsu.dao.UserDao;
import com.dompine.himitsu.entity.User;
import com.dompine.himitsu.service.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

    @Value("${appid}")
    String appid;
    @Value("${secret}")
    String secret;

    @RequestMapping(value = "onLogin")
    @ResponseBody
    User getByCode(@RequestParam("code") String code) {

//把收到的json对象转换为map对象
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret=" + secret + "&js_code="
                + code + "&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        Gson gson = new Gson();
        Map map = gson.fromJson(response, Map.class);
        //如果没登录过，就在数据库保存一个
        if (!userDao.findByUserOpenid((String) map.get("openid")).isPresent()) {
            User user = new User();
            user.setUserOpenid((String) map.get("openid"));
            userDao.save(user);
            return userDao.save(user);
        }
        return userDao.findByUserOpenid((String) map.get("openid")).get();
    }

    @RequestMapping(value = "userinfo")
    @ResponseBody
    void userInfo(@RequestParam("userId") long userId, @RequestParam("avatarUrl") String avatarUrl, @RequestParam("gender") Integer gender, @RequestParam("nickName") String nickName) {
        if (userDao.findById(userId).isPresent()) {
            User user = userDao.findById(userId).get();
            user.setAvatarUrl(avatarUrl);
            user.setGender(gender);
            user.setNickName(nickName);
            userDao.save(user);
        }
    }

    @RequestMapping(value = "trade")
    @ResponseBody
    User trade(@RequestParam("userId")long userId) {
      return userService.trade(userId);
    }

    @RequestMapping(value = "getTickets")
    @ResponseBody
    User getTickets(@RequestParam("userId")long userId){
        User user = userDao.findById(userId).get();
        return userService.getTickets(user,1);
    }
}
