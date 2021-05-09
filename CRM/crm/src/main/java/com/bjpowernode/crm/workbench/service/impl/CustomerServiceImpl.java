package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.workbench.domain.Customer;
import com.bjpowernode.crm.workbench.mapper.CustomerMapper;
import com.bjpowernode.crm.workbench.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 姜宝
 * 2021/4/24
 */
@Service
public class CustomerServiceImpl  implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public int insertCustomer(Customer customer) {
        return customerMapper.insertCustomer(customer);
    }

    @Override
    public List<Customer> selectCustomerByName(String name) {
        return customerMapper.selectCustomerByName(name);
    }
}
