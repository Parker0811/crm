package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.settings.domain.TblUser;
import com.bjpowernode.crm.settings.mapper.TblUserMapper;
import com.bjpowernode.crm.settings.service.TblUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 姜宝
 * 2021/4/9
 */
@Service
public class TblTblUserServiceImpl implements TblUserService {
    @Autowired
    private TblUserMapper tblUserMapper;

    @Override
    public TblUser selectById(String id) {
        return tblUserMapper.selectByPrimaryKey(id);
    }
}
