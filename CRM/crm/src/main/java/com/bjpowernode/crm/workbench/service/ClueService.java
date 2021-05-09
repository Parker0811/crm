package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Clue;

import java.util.Map;

/**
 * 姜宝
 * 2021/4/22
 */
public interface ClueService {
    /**
     * 保存创建的线索
     */
    int insertClue(Clue clue);

    /**
     * 根据id查询线索明细信息
     */
    Clue selectClueForDetailById(String id);

    /**
     * 根据id查询线索信息
     */
    Clue selectClueById(String id);

    /**
     * 根据id删除线索
     */
    int deleteClueById(String id);

    //线索转换的业务处理
    void saveConvertClue(Map<String,Object> map);
}
