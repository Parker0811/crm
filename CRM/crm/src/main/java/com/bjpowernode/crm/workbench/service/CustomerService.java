package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Customer;

import java.util.List;

/**
 * 姜宝
 * 2021/4/24
 */
public interface CustomerService {
    /**
     * 保存创建的客户
     */
    int insertCustomer(Customer customer);

    /**
     * 根据name模糊查询客户
     */
    List<Customer> selectCustomerByName(String name);
}
