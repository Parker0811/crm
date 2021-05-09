package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.workbench.domain.CustomerRemark;
import com.bjpowernode.crm.workbench.mapper.CustomerRemarkMapper;
import com.bjpowernode.crm.workbench.service.CustomerRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.WebServiceRef;
import java.util.List;

/**
 * 姜宝
 * 2021/4/24
 */
@Service
public class CustomerRemarkServiceImpl  implements CustomerRemarkService {

    @Autowired
    private CustomerRemarkMapper customerRemarkMapper;


    @Override
    public int insertCustomerRemarkByList(List<CustomerRemark> remarkList) {
        return customerRemarkMapper.insertCustomerRemarkByList(remarkList);
    }
}
