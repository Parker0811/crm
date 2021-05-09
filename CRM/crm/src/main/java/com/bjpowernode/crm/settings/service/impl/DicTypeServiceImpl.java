package com.bjpowernode.crm.settings.service.impl;


import com.bjpowernode.crm.settings.domain.DicType;
import com.bjpowernode.crm.settings.mapper.DicTypeMapper;
import com.bjpowernode.crm.settings.service.DicTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 姜宝
 * 2021/4/15
 */
@Service
public class DicTypeServiceImpl implements DicTypeService {
    @Autowired
    private DicTypeMapper dicTypeMapper;
    @Override
    public int insertDicType(DicType dicType) {
        return dicTypeMapper.insertDicType(dicType);
    }

    @Override
    public int deleteDicTypesByCode(String[] codes) {
        return dicTypeMapper.deleteDicTypesByCode(codes);
    }

    @Override
    public DicType selectDicTypeByCode(String code) {
        return dicTypeMapper.selectDicTypeByCode(code);
    }

    @Override
    public int updateDicType(DicType dicType) {
        return dicTypeMapper.updateDicType(dicType);
    }

    @Override
    public List<DicType> selectAllDicTypes() {
        return dicTypeMapper.selectAllDicTypes();
    }
}
