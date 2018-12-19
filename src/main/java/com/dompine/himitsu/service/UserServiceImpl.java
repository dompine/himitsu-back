package com.dompine.himitsu.service;

import com.dompine.himitsu.dao.UserDao;
import com.dompine.himitsu.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User onLogin(String code) {

        return null;
    }

    @Override
    public User findByUseropenid(String userOpenid) {
        return null;
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public User trade(long userId) {
        User user = userDao.findById(userId).get();
        user.setTickets(user.getTickets()-1);
        user.setTimes(user.getTimes()+1);
        userDao.save(user);
        return user;
    }

    @Override
    public User getTickets(User user,int num) {

        if(user.getMaxTickets()<=0){         //如果今日获得门票达到上限
            return user;
        }
        user.setMaxTickets(user.getMaxTickets()-num);  //减去获得门票数
        user.setTickets(user.getTickets()+num);       //每次写获得两张邮票
        userDao.save(user);
        return user;
    }
}
