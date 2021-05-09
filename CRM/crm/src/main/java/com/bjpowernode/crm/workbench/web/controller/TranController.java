package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.mapper.DicValueMapper;
import com.bjpowernode.crm.settings.service.DicValueService;
import com.bjpowernode.crm.settings.service.impl.UserService;
import com.bjpowernode.crm.workbench.domain.Customer;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.domain.TranHistory;
import com.bjpowernode.crm.workbench.domain.TranRemark;
import com.bjpowernode.crm.workbench.service.CustomerService;
import com.bjpowernode.crm.workbench.service.TranHistoryService;
import com.bjpowernode.crm.workbench.service.TranRemarkService;
import com.bjpowernode.crm.workbench.service.TranService;
import com.sun.javafx.logging.PulseLogger;
import com.sun.xml.internal.ws.resources.HttpserverMessages;
import org.apache.commons.collections4.Put;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.swing.tree.TreeNode;
import java.awt.event.MouseEvent;
import java.util.*;

/**
 * 姜宝
 * 2021/4/25
 */
@Controller
public class TranController {

    @Autowired
    private DicValueService dicValueService;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    TranService tranService;

    @Autowired
    private TranHistoryService tranHistoryService;

    @Autowired
    private TranRemarkService tranRemarkService;

    //跳转到交易页面
    @RequestMapping("/workbench/transaction/index.do")
    public String index(Model model){
        List<DicValue> stageList=dicValueService.selectDicValueByTypeCode("stage");
        List<DicValue> transactionTypeList=dicValueService.selectDicValueByTypeCode("transactionType");
        List<DicValue> sourceList=dicValueService.selectDicValueByTypeCode("source");
        //把数据保存到request中
        model.addAttribute("stageList",stageList);
        model.addAttribute("transactionTypeList",transactionTypeList);
        model.addAttribute("sourceList",sourceList);
        return "workbench/transaction/index";
    }

    //通过创建按钮跳转到save界面
    @RequestMapping("/workbench/transaction/createTran.do")
    public String createTran(Model model){
        List<DicValue> stageList=dicValueService.selectDicValueByTypeCode("stage");
        List<DicValue> transactionTypeList=dicValueService.selectDicValueByTypeCode("transactionType");
        List<DicValue> sourceList=dicValueService.selectDicValueByTypeCode("source");
        List<User> userList = userService.selectAllUsers();
        //把数据保存到request中
        model.addAttribute("stageList",stageList);
        model.addAttribute("transactionTypeList",transactionTypeList);
        model.addAttribute("sourceList",sourceList);
        model.addAttribute("userList", userList);
        return "workbench/transaction/save";
    }

    //自动补全
    @RequestMapping("/workbench/transaction/queryCustomerByName.do")
    public @ResponseBody Object queryCustomerByName(String customerName){
        List<Customer> customerList = customerService.selectCustomerByName(customerName);
        return customerList;
    }

    //根据阶段,跳出指定的可能性值
    @RequestMapping("/workbench/transaction/getPossibilityByStageValue.do")
    public @ResponseBody Object getPossibilityByStageValue(String stageValue){
        ResourceBundle bundle = ResourceBundle.getBundle("possibility");
        String possiblity = bundle.getString(stageValue);
        return possiblity;
    }

    //通过保存按钮添加一条交易到数据库中
    @RequestMapping("/workbench/transaction/saveCreateTran.do")
    public @ResponseBody Object saveCreateTran(Tran tran,String customerName,HttpSession session){
        ReturnObject returnObject = new ReturnObject();
        User user=(User)session.getAttribute(Contants.SESSION_USER);
        //封装参数
        tran.setId(UUIDUtils.getUUID());
        tran.setCreateBy(user.getId());
        tran.setCreateTime(DateUtils.formatDateTime(new Date()));
        Map<String,Object> map=new HashMap<>();
        map.put("tran",tran);
        map.put("customerName",customerName);
        map.put("sessionUser",user);
        try {
            tranService.saveCreateTran(map);
            returnObject.setCode(Contants.RETURNOBJECT_CODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURNOBJECT_CODE_FAIL);
            returnObject.setMessage("保存失败");
        }
        return returnObject;
    }

    //跳转到交易详细表
    @RequestMapping("/workbench/transaction/detailTran.do")
    public String detailTran(String id,Model model){
        Tran tran = tranService.selectTranForDetailById(id);
        String stage = tran.getStage();
        ResourceBundle bundle = ResourceBundle.getBundle("possibility");
        String possibility = bundle.getString(stage);
        tran.setPossibility(possibility);
        List<TranHistory> tranHistoryList = tranHistoryService.selectTranHistoryForDetailByTranId(id);
        List<TranRemark> remarkList = tranRemarkService.selectTranRemarkForDetailByTranId(id);
        List<DicValue> stageList = dicValueService.selectDicValueByTypeCode("stage");
        model.addAttribute("tran", tran);
        model.addAttribute("tranHistoryList", tranHistoryList);
        model.addAttribute("remarkList", remarkList);
        model.addAttribute("stageList", stageList);
        TranHistory tranHistory = tranHistoryList.get(tranHistoryList.size() - 1);
        String theOrderOne = tranHistory.getOrderNo();
        model.addAttribute("theOrderNo ", theOrderOne);

        return "workbench/transaction/detail";
    }
}
