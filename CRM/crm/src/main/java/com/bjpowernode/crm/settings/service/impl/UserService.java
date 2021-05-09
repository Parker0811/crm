package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.settings.domain.User;

import java.util.List;
import java.util.Map;

/**
 * 姜宝
 * 2021/4/10
 */
public interface UserService {
    //根据用户名与密码进行用户验证
    User selectUserByLoginActAndPwd(Map<String, Object> map);

    //查询所有用户
    List<User> selectAllUsers();
}
