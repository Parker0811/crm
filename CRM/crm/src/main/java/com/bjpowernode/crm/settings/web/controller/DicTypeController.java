package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.settings.domain.DicType;
import com.bjpowernode.crm.settings.service.DicTypeService;
import org.apache.commons.collections4.Put;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 姜宝
 * 2021/4/16
 * 字典类型模块控制层
 */
@Controller
public class DicTypeController {
    @Autowired
    private DicTypeService dicTypeService;

    //返回数据字典表界面
    @RequestMapping("settings/dictionary/index.do")
    public String index() {
        return "settings/dictionary/index";
    }

    //返回字典类型界面
    @RequestMapping("settings/dictionary/type/index.do")
    public String typeIndex(Model model) {
        List<DicType> dicTypeList = dicTypeService.selectAllDicTypes();
        model.addAttribute("dicTypeList", dicTypeList);
        return "settings/dictionary/type/index";
    }

    //返回创建页面
    @RequestMapping("settings/dictionary/type/toSave.do")
    public String toSave() {
        return "settings/dictionary/type/save";
    }

    //验证编码是否重复
    @RequestMapping("settings/dictionary/type/checkCode.do")
    public @ResponseBody
    ReturnObject checkCode(String code) {
        ReturnObject returnObject = new ReturnObject();
        DicType dicType = dicTypeService.selectDicTypeByCode(code);
        if (dicType == null) {
            //说明表中没有该编码
            returnObject.setCode(Contants.RETURNOBJECT_CODE_SUCCESS);
        } else {
            //表中有该编码
            returnObject.setCode(Contants.RETURNOBJECT_CODE_FAIL);
            returnObject.setMessage("编码已经存在");
        }
        return returnObject;
    }

    //根据保存按钮添加一条数据
    @RequestMapping("settings/dictionary/type/saveCreateDicType.do")
    public @ResponseBody
    ReturnObject saveCreateDicType(DicType dicType) {
        System.out.println(123);
        ReturnObject returnObject = new ReturnObject();

        int result = dicTypeService.insertDicType(dicType);
        if (result > 0) {
            //插入成功
            returnObject.setCode(Contants.RETURNOBJECT_CODE_SUCCESS);
        } else {
            returnObject.setCode(Contants.RETURNOBJECT_CODE_FAIL);
            returnObject.setMessage("保存失败");
        }
        return returnObject;
    }

    //返回字典类型编辑页面
    @RequestMapping("settings/dictionary/type/editDicType.do")
    public String editDicType(String code, Model model) {
        DicType dicType = dicTypeService.selectDicTypeByCode(code);
        model.addAttribute("dicType", dicType);
        System.out.println(dicType);
        return "settings/dictionary/type/edit";
    }

    //根据更新按钮添加一条数据
    @RequestMapping("settings/dictionary/type/saveEditDicType.do")
    public @ResponseBody
    ReturnObject saveEditDicType(DicType dicType) {
        ReturnObject returnObject = new ReturnObject();
        int result = dicTypeService.updateDicType(dicType);
        if (result > 0) {
            returnObject.setCode(Contants.RETURNOBJECT_CODE_SUCCESS);
        } else {
            returnObject.setCode(Contants.RETURNOBJECT_CODE_FAIL);
            returnObject.setMessage("更新失败");
        }
        return returnObject;
    }

    //根据删除按钮删除数据
    @RequestMapping("settings/dictionary/type/deleteDicTypeByCodes.do")
    public @ResponseBody
    ReturnObject deleteDicTypeByCodes(String[] code) {
        ReturnObject returnObject = new ReturnObject();
        int result = dicTypeService.deleteDicTypesByCode(code);
        if (result > 0) {
            returnObject.setCode(Contants.RETURNOBJECT_CODE_SUCCESS);
        } else {
            returnObject.setCode(Contants.RETURNOBJECT_CODE_FAIL);
            returnObject.setMessage("删除失败");
        }
        return returnObject;
    }
}
