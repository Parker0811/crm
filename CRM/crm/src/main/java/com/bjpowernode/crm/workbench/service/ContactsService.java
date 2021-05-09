package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Contacts;

/**
 * 姜宝
 * 2021/4/24
 */
public interface ContactsService {

    /**
     * 保存创建的联系人
     */
    int insertContacts(Contacts contacts);
}
