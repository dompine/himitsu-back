package com.dompine.himitsu.service;

import com.dompine.himitsu.entity.User;

public interface UserService {

    User onLogin(String code);

    User findByUseropenid(String userOpenid);

    User save(User user);

    User trade(long userId);

    User getTickets(User user,int num);
}
