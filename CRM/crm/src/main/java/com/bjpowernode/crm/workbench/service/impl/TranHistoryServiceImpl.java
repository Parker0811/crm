package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.workbench.domain.TranHistory;
import com.bjpowernode.crm.workbench.mapper.TranHistoryMapper;
import com.bjpowernode.crm.workbench.service.TranHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 姜宝
 * 2021/4/26
 */
@Service
public class TranHistoryServiceImpl implements TranHistoryService {

    @Autowired
    private TranHistoryMapper tranHistoryMapper;

    @Override
    public int insertTranHistory(TranHistory tranHistory) {
        return tranHistoryMapper.insertTranHistory(tranHistory);
    }

    @Override
    public List<TranHistory> selectTranHistoryForDetailByTranId(String tranId) {
        return tranHistoryMapper.selectTranHistoryForDetailByTranId(tranId);
    }
}
