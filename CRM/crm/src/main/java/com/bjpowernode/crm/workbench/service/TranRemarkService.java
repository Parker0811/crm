package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.TranRemark;

import java.util.List;

/**
 * 姜宝
 * 2021/4/24
 */
public interface TranRemarkService {

    /**
     * 批量保存创建的交易备注
     */
    int insertTranRemarkByList(List<TranRemark> remarkList);

    /**
     * 根据tranId查询该交易下所有备注的明细信息
     */
    List<TranRemark> selectTranRemarkForDetailByTranId(String tranId);
}
