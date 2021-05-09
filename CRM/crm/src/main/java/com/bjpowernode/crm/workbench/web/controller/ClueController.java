package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.DicTypeService;
import com.bjpowernode.crm.settings.service.DicValueService;
import com.bjpowernode.crm.settings.service.impl.UserService;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.ClueActivityRelationService;
import com.bjpowernode.crm.workbench.service.ClueService;
import com.bjpowernode.crm.workbench.service.impl.ClueServiceImpl;
import com.sun.javafx.tk.TKPulseListener;
import com.sun.xml.internal.ws.resources.HttpserverMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 姜宝
 * 2021/4/21
 */
@Controller
public class ClueController {

    @Autowired
    private ClueActivityRelationService clueActivityRelationService;

    @Autowired
    private ClueService clueService;

    @Autowired
    private UserService userService;

    @Autowired
    private DicValueService dicValueService;

    @Autowired
    private ActivityService activityService;

    //返回线索的主页面
    @RequestMapping("/workbench/clue/index.do")
    public String index(Model model) {
        List<User> userList = userService.selectAllUsers();
        model.addAttribute("userList", userList);
        List<DicValue> appellationList = dicValueService.selectDicValueByTypeCode("appellation");
        List<DicValue> sourceList = dicValueService.selectDicValueByTypeCode("source");
        List<DicValue> clueStateList = dicValueService.selectDicValueByTypeCode("clueState");
        model.addAttribute("appellationList", appellationList);
        model.addAttribute("clueStateList", clueStateList);
        model.addAttribute("sourceList", sourceList);
        return "workbench/clue/index";
    }

    //根据保存按钮,向数据库中插入一条数据
    @RequestMapping("/workbench/clue/saveCreateClue.do")
    public @ResponseBody
    Object saveCreateClue(Clue clue, HttpSession session) {
        User user = (User) session.getAttribute(Contants.SESSION_USER);
        clue.setId(UUIDUtils.getUUID());
        clue.setCreateBy(user.getId());
        clue.setCreateTime(DateUtils.formatDateTime(new Date()));
        ReturnObject returnObject = new ReturnObject();
        int result = clueService.insertClue(clue);
        if (result > 0) {
            returnObject.setCode(Contants.RETURNOBJECT_CODE_SUCCESS);
        } else {
            returnObject.setCode(Contants.RETURNOBJECT_CODE_FAIL);
            returnObject.setMessage("保存失败");
        }

        return returnObject;
    }

    //通过点击名称查看详细信息
    @RequestMapping("workbench/clue/detailClue.do")
    public String detailClue(String id, Model model) {
        Clue clue = clueService.selectClueForDetailById(id);
        List<Activity> activityList = activityService.selectActivityForDetailByClueId(id);
        model.addAttribute("clue", clue);
        model.addAttribute("activityList", activityList);
        return "workbench/clue/detail";
    }

    //显示根据名称模糊查询的没有与指定id的线索关联的市场活动
    @RequestMapping("/workbench/clue/searchActivity.do")
    public @ResponseBody
    Object searchActivity(String activityName, String clueId) {
        Map<String, Object> map = new HashMap<>();
        map.put("activityName", activityName);
        map.put("clueId", clueId);
        List<Activity> activityList = activityService.selectActivityForDetailByNameClueId(map);
        return activityList;
    }

    //给关联按钮添加点击事件
    @RequestMapping("/workbench/clue/saveBundActivity.do")
    public @ResponseBody
    Object saveBundActivity(String[] activityId, String clueId) {
        List<ClueActivityRelation> relationList = new ArrayList<>();
        ClueActivityRelation relation = null;
        for (String ai : activityId) {
            relation = new ClueActivityRelation();
            relation.setId(UUIDUtils.getUUID());
            relation.setActivityId(ai);
            relation.setClueId(clueId);
            relationList.add(relation);
        }

        ReturnObject returnObject = new ReturnObject();
        try {
            //调用service层方法，保存线索和市场活动的关联关系
            int ret = clueActivityRelationService.insertClueActivityRelationByList(relationList);
            if (ret > 0) {
                returnObject.setCode(Contants.RETURNOBJECT_CODE_SUCCESS);
                //查询activityId市场活动的信息
                List<Activity> activityList = activityService.selectActivityForDetailByIds(activityId);
                returnObject.setReturnData(activityList);
            } else {
                returnObject.setCode(Contants.RETURNOBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后重试....");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURNOBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试....");
        }
        return returnObject;
    }

    //通过点击解除关联,删除线索与市场活动关联表中的一行数据
    @RequestMapping("/workbench/clue/saveUnbundActivity.do")
    public @ResponseBody
    Object saveUnbundActivity(String activityId, String clueId) {
        ReturnObject returnObject = new ReturnObject();
        ClueActivityRelation relation = new ClueActivityRelation();
        relation.setActivityId(activityId);
        relation.setClueId(clueId);
        int result = clueActivityRelationService.deleteClueActivityRelationByClueIdActivityId(relation);
        if (result > 0) {
            returnObject.setCode(Contants.RETURNOBJECT_CODE_SUCCESS);
        } else {
            returnObject.setCode(Contants.RETURNOBJECT_CODE_FAIL);
            returnObject.setMessage("解除失败");
        }
        return returnObject;
    }

    //通过转换按钮跳转到转换页面
    @RequestMapping("/workbench/clue/convertClue.do")
    public String convertClue(String id, Model model) {
        Clue clue = clueService.selectClueForDetailById(id);
        List<DicValue> stageList = dicValueService.selectDicValueByTypeCode("stage");
        model.addAttribute("clue", clue);
        model.addAttribute("stageList", stageList);
        return "workbench/clue/convert";
    }

    //通过点击转换按钮,实现转化业务
    @RequestMapping("/workbench/clue/saveConvertClue.do")
    public @ResponseBody
    Object saveConvertClue(String clueId, String isCreateTran, String amountOfMoney, String tradeName, String expectedClosingDate, String stage, String activityId, HttpSession session) {
        //封装参数
        Map<String, Object> map = new HashMap<>();
        map.put("clueId", clueId);
        map.put("isCreateTran", isCreateTran);
        map.put("amountOfMoney", amountOfMoney);
        map.put("tradeName", tradeName);
        map.put("expectedClosingDate", expectedClosingDate);
        map.put("stage", stage);
        map.put("activityId", activityId);
        map.put("sessionUser", session.getAttribute(Contants.SESSION_USER));
        ReturnObject returnObject = new ReturnObject();


        try {
            //调用业务类进行线索转换的业务处理
            clueService.saveConvertClue(map);
            returnObject.setCode(Contants.RETURNOBJECT_CODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURNOBJECT_CODE_FAIL);
            returnObject.setMessage("转换失败");
        }
        return returnObject;

    }

}
