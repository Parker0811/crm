package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.ClueRemark;

import java.util.List;

/**
 * 姜宝
 * 2021/4/24
 */
public interface ClueRemarkService {

    /**
     * 根据clueId查询该线索下所有备注明细信息
     */
    List<ClueRemark> selectClueRemarkForDetailByClueId(String clueId);

    /**
     * @根据clueId查询该线索下所有的备注
     */
    List<ClueRemark> selectClueRemarkByClueId(String clueId);

    /**
     * 根据clueId删除该线索下所有的备注
     */
    int deleteClueRemarkByClueId(String clueId);
}
