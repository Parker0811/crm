package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.workbench.domain.ContactsActivityRelation;
import com.bjpowernode.crm.workbench.mapper.ContactsActivityRelationMapper;
import com.bjpowernode.crm.workbench.service.ContactsActivityRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 姜宝
 * 2021/4/24
 */
@Service
public class ContactsActivityRelationServiceImpl  implements ContactsActivityRelationService {

    @Autowired
    private ContactsActivityRelationMapper contactsActivityRelationMapper;

    @Override
    public int insertContactsActivityRelationByList(List<ContactsActivityRelation> relationList) {
        return contactsActivityRelationMapper.insertContactsActivityRelationByList(relationList);
    }
}
