package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.workbench.domain.ContactsRemark;
import com.bjpowernode.crm.workbench.mapper.ContactsRemarkMapper;
import com.bjpowernode.crm.workbench.service.ContactsRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 姜宝
 * 2021/4/24
 */
@Service
public class ContactsRemarkServiceImpl implements ContactsRemarkService {

    @Autowired
    private ContactsRemarkMapper contactsRemarkMapper;

    @Override
    public int insertContactsRemarkByList(List<ContactsRemark> remarkList) {
        return contactsRemarkMapper.insertContactsRemarkByList(remarkList);
    }
}
