package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.DicType;
import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.service.DicTypeService;
import com.bjpowernode.crm.settings.service.DicValueService;
import org.apache.commons.collections4.Put;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.swing.event.MouseInputListener;
import java.util.List;
import java.util.UUID;

/**
 * 姜宝
 * 2021/4/16
 */
@Controller
public class DicValueController {
    @Autowired
    private DicValueService dicValueService;

    @Autowired
    private DicTypeService dicTypeService;

    //返回字典值主页面
    @RequestMapping("settings/dictionary/value/index.do")
    public String index(Model model){
        List<DicValue> dicValueList = dicValueService.selectAllDicValues();
        model.addAttribute("dicValueList",dicValueList);
        return "settings/dictionary/value/index";
    }

    //返回创建界面
    @RequestMapping("settings/dictionary/value/toSave.do")
    public String toSave(Model model){
        List<DicType> dicTypeList = dicTypeService.selectAllDicTypes();
        model.addAttribute("dicTypeList",dicTypeList);
        return "settings/dictionary/value/save";
    }

    //根据保存按钮添加一条数据
    @RequestMapping("settings/dictionary/value/saveCreateDicValue.do")
    public @ResponseBody
    /*  typeCode:typeCode,
	    value:value,
		text:text,
		orderNo:orderNo */
    ReturnObject saveCreateDicValue(String typeCode,String value,String text,String orderNo){
        String uuid = UUIDUtils.getUUID();
        DicValue dicValue = new DicValue();
        dicValue.setId(uuid);
        dicValue.setOrderNo(orderNo);
        dicValue.setText(text);
        dicValue.setTypeCode(typeCode);
        dicValue.setValue(value);
        ReturnObject returnObject = new ReturnObject();
        int result = dicValueService.insertDicValue(dicValue);
        if (result>0){
            //数据添加成功
            returnObject.setCode(Contants.RETURNOBJECT_CODE_SUCCESS);
        }else{
            //数据添加失败
            returnObject.setCode(Contants.RETURNOBJECT_CODE_FAIL);
            returnObject.setMessage("添加失败");
        }
        return returnObject;
    }

    //返回编辑页面
    @RequestMapping("settings/dictionary/value/editDicValue.do")
    public String editDicValue(String id,Model model){
        DicValue dicValue = dicValueService.selectDicValueById(id);
        model.addAttribute("dicValue",dicValue);
        return "settings/dictionary/value/edit";
    }

    //通过更新按钮更新一条数据
    @RequestMapping("settings/dictionary/value/saveEditDicValue.do")
    public @ResponseBody ReturnObject saveEditDicValue(DicValue dicValue){
        ReturnObject returnObject = new ReturnObject();
        int result = dicValueService.updateDicValue(dicValue);
        if (result>0){
            //更新成功
            returnObject.setCode(Contants.RETURNOBJECT_CODE_SUCCESS);
        }else{
            //更新失败
            returnObject.setCode(Contants.RETURNOBJECT_CODE_FAIL);
            returnObject.setMessage("更新失败");
        }
     return returnObject;
    }

    //通过删除按钮批量删除
    @RequestMapping("settings/dictionary/value/deleteDicValueByIds.do")
    public @ResponseBody ReturnObject deleteDicValueByIds(String[] id){
        ReturnObject returnObject = new ReturnObject();
        int result = dicValueService.deleteDicValueByIds(id);
        if (result>0){
            returnObject.setCode(Contants.RETURNOBJECT_CODE_SUCCESS);
        }else{
            returnObject.setCode(Contants.RETURNOBJECT_CODE_FAIL);
            returnObject.setMessage("删除失败");
        }
        return returnObject;
    }
}
