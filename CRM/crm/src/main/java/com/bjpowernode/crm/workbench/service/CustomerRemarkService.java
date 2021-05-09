package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.CustomerRemark;

import java.util.List;

/**
 * 姜宝
 * 2021/4/24
 */
public interface CustomerRemarkService {
    /**
     * 批量保存创建的客户备注
     */
    int insertCustomerRemarkByList(List<CustomerRemark> remarkList);
}
