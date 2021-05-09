package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.settings.domain.DicValue;

import java.util.List;

/**
 * 姜宝
 * 2021/4/15
 */
public interface DicValueService {
    List<DicValue> selectAllDicValues();

    int insertDicValue(DicValue dicValue);


    int deleteDicValueByIds(String[] ids);


    DicValue selectDicValueById(String id);


    int updateDicValue(DicValue dicValue);

    int deleteDicValueByTypeCodes(String[] typeCodes);

    List<DicValue> selectDicValueByTypeCode(String typeCode);
}
