package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.TranHistory;

import java.util.List;

/**
 * 姜宝
 * 2021/4/26
 */
public interface TranHistoryService {

    /**
     * 保存创建的交易历史
     */
    int insertTranHistory(TranHistory tranHistory);

    /**
     * 根据tranId查询该交易下所有的历史记录明细信息
     */
    List<TranHistory> selectTranHistoryForDetailByTranId(String tranId);
}
