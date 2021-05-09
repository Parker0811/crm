package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.workbench.domain.TranRemark;
import com.bjpowernode.crm.workbench.mapper.TranRemarkMapper;
import com.bjpowernode.crm.workbench.service.TranRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 姜宝
 * 2021/4/24
 */
@Service
public class TranRemarkServiceImpl implements TranRemarkService {

    @Autowired
    private TranRemarkMapper tranRemarkMapper;

    @Override
    public int insertTranRemarkByList(List<TranRemark> remarkList) {
        return tranRemarkMapper.insertTranRemarkByList(remarkList);
    }

    @Override
    public List<TranRemark> selectTranRemarkForDetailByTranId(String tranId) {
        return tranRemarkMapper.selectTranRemarkForDetailByTranId(tranId);
    }
}
