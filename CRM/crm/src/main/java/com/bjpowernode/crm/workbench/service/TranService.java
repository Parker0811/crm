package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.FunnelVO;
import com.bjpowernode.crm.workbench.domain.Tran;

import java.util.List;
import java.util.Map;

/**
 * 姜宝
 * 2021/4/24
 */
public interface TranService {

    /**
     *保存创建交易
     */
    int insertTran(Tran tran);

    /**
     * 根据id查询交易明细信息
     */
    Tran selectTranForDetailById(String id);

    /**
     * 查询交易表中各个阶段的数据量
     */
    List<FunnelVO> selectCountOfTranGroupByStage();

    void saveCreateTran(Map<String, Object> map);
}
