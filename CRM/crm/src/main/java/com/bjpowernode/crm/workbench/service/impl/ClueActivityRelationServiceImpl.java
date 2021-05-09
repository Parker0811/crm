package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;
import com.bjpowernode.crm.workbench.mapper.ClueActivityRelationMapper;
import com.bjpowernode.crm.workbench.service.ClueActivityRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 姜宝
 * 2021/4/22
 */
@Service
public class ClueActivityRelationServiceImpl  implements ClueActivityRelationService {

    @Autowired
    private ClueActivityRelationMapper clueActivityRelationMapper;

    @Override
    public int insertClueActivityRelationByList(List<ClueActivityRelation> relationList) {
        return clueActivityRelationMapper.insertClueActivityRelationByList(relationList);
    }

    @Override
    public int deleteClueActivityRelationByClueIdActivityId(ClueActivityRelation relation) {
        return clueActivityRelationMapper.deleteClueActivityRelationByClueIdActivityId(relation);
    }

    @Override
    public List<ClueActivityRelation> selectClueActivityRelationByClueId(String clueId) {
        return clueActivityRelationMapper.selectClueActivityRelationByClueId(clueId);
    }

    @Override
    public int deleteClueActivityRelationByClueId(String clueId) {
        return clueActivityRelationMapper.deleteClueActivityRelationByClueId(clueId);
    }
}
