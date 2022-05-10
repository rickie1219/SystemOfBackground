package com.example.service;

import com.example.entity.User;
import com.example.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    LoginMapper loginMapper;

    public User userLogin(User user) {
        User user2 = loginMapper.findUserByUserName(user);
        return user2;
    }

    public User getAccountByEmail(String email) {
        return loginMapper.selectUserByEmail(email);
    }

    public Integer registerUser(String email, String md5Pwd) {
        return loginMapper.registerUser(email, md5Pwd);
    }

    public Integer getUserIdByAccount(String email) {
        return loginMapper.findIdByEmail(email);
    }

    public Integer userForgetPassword(String email, String md5Pwd, Integer userID) {
        return loginMapper.modifyAccountPassword(email, md5Pwd, userID);
    }
}
