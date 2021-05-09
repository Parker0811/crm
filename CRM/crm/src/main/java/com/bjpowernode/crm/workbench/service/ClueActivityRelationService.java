package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;

import java.util.List;

/**
 * 姜宝
 * 2021/4/22
 */
public interface ClueActivityRelationService {
    /**
     * 批量保存创建的线索和市场活动的关联关系
     */
    int insertClueActivityRelationByList(List<ClueActivityRelation> relationList);

    /**
     * 根据clueId和activityId删除线索和市场活动的关联关系
     */
    int deleteClueActivityRelationByClueIdActivityId(ClueActivityRelation relation);

    /**
     * 根据clueId查询线索和市场活动的关联关系
     */
    List<ClueActivityRelation> selectClueActivityRelationByClueId(String clueId);

    /**
     * 根据clueId删除线索和市场活动的关联关系
     */
    int deleteClueActivityRelationByClueId(String clueId);
}
