package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 姜宝
 * 2021/4/10
 */
@Service
public class UserServiceImpl  implements UserService{
    @Autowired
    private UserMapper userMapper;

    @Override
    public User selectUserByLoginActAndPwd(Map<String, Object> map) {
        return userMapper.selectUserByLoginActAndPwd(map);
    }

    @Override
    public List<User> selectAllUsers() {
        return userMapper.selectAllUsers();
    }
}
