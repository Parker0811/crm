package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.ContactsRemark;

import java.util.List;

/**
 * 姜宝
 * 2021/4/24
 */
public interface ContactsRemarkService {

    /**
     * 批量保存创建联系人备注
     */
    int insertContactsRemarkByList(List<ContactsRemark> remarkList);
}
