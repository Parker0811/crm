package com.bjpowernode.crm.settings.service;



import com.bjpowernode.crm.settings.domain.DicType;

import java.util.List;

/**
 * 姜宝
 * 2021/4/15
 */
public interface DicTypeService {
    //增
    int insertDicType(DicType dicType);

    //删
    int deleteDicTypesByCode(String[] codes);

    //改
    DicType selectDicTypeByCode(String code);
    int updateDicType(DicType dicType);

    //查
    List<DicType> selectAllDicTypes();
}
