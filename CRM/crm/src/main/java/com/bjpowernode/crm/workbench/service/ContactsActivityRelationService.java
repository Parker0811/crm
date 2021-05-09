package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.ContactsActivityRelation;

import java.util.List;

/**
 * 姜宝
 * 2021/4/24
 */
public interface ContactsActivityRelationService {
    /**
     * 批量添加联系人和市场活动的关联关系
     */
    int insertContactsActivityRelationByList(List<ContactsActivityRelation> relationList);
}
