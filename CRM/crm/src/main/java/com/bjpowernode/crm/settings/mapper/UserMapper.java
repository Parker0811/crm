package com.bjpowernode.crm.settings.mapper;

import com.bjpowernode.crm.settings.domain.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    //根据用户名与密码进行用户登陆验证
    User selectUserByLoginActAndPwd(Map<String,Object> map);

    //查询所有用户
    List<User> selectAllUsers();
}