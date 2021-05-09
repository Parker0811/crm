package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.mapper.DicValueMapper;
import com.bjpowernode.crm.settings.service.DicValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 姜宝
 * 2021/4/15
 */
@Service
public class DicValueServiceImpl implements DicValueService {
    @Autowired
    private DicValueMapper dicValueMapper;

    @Override
    public List<DicValue> selectAllDicValues() {
        return dicValueMapper.selectAllDicValues();
    }

    @Override
    public int insertDicValue(DicValue dicValue) {
        return dicValueMapper.insertDicValue(dicValue);
    }

    @Override
    public int deleteDicValueByIds(String[] ids) {
        return dicValueMapper.deleteDicValueByIds(ids);
    }

    @Override
    public DicValue selectDicValueById(String id) {
        return dicValueMapper.selectDicValueById(id);
    }

    @Override
    public int updateDicValue(DicValue dicValue) {
        return dicValueMapper.updateDicValue(dicValue);
    }

    @Override
    public int deleteDicValueByTypeCodes(String[] typeCodes) {
        return dicValueMapper.deleteDicValueByTypeCodes(typeCodes);
    }

    @Override
    public List<DicValue> selectDicValueByTypeCode(String typeCode) {
        return dicValueMapper.selectDicValueByTypeCode(typeCode);
    }
}
