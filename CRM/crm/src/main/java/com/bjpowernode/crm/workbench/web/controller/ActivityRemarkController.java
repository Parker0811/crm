package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.mapper.ActivityRemarkMapper;
import com.bjpowernode.crm.workbench.service.ActivityRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * 姜宝
 * 2021/4/24
 */
@Controller
public class ActivityRemarkController {

    @Autowired
    private ActivityRemarkService activityRemarkService;

    //通过保存按钮,创建一条评论到activity_remark表中
    @RequestMapping("/workbench/activity/saveCreateActivityRemark.do")
    public @ResponseBody
    Object saveCreateActivityRemark(String noteContent, String activityId, HttpSession session) {
        ReturnObject returnObject = new ReturnObject();
        ActivityRemark remark = new ActivityRemark();
        remark.setId(UUIDUtils.getUUID());
        remark.setNoteContent(noteContent);
        remark.setCreateTime(DateUtils.formatDateTime(new Date()));
        User user = (User) session.getAttribute(Contants.SESSION_USER);
        remark.setCreateBy(user.getId());
        remark.setEditFlag("0");
        remark.setActivityId(activityId);
        try {
            int result = activityRemarkService.insertActivityRemark(remark);
            if (result > 0) {
                returnObject.setCode(Contants.RETURNOBJECT_CODE_SUCCESS);
                returnObject.setReturnData(remark);
            } else {
                returnObject.setCode(Contants.RETURNOBJECT_CODE_FAIL);
                returnObject.setMessage("保存失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURNOBJECT_CODE_FAIL);
            returnObject.setMessage("保存失败");
        }
        return returnObject;
    }

    //通过更新按钮修改备注内容
    @RequestMapping("/workbench/activity/saveEditActivityRemark.do")
    public @ResponseBody
    Object saveEditActivityRemark(String id, String noteContent, HttpSession session) {
        ReturnObject returnObject = new ReturnObject();
        try {
            ActivityRemark activityRemark = activityRemarkService.selectByPrimaryKey(id);
            activityRemark.setNoteContent(noteContent);
            activityRemark.setEditFlag("1");
            activityRemark.setEditTime(DateUtils.formatDateTime(new Date()));
            User user = (User) session.getAttribute(Contants.SESSION_USER);
            activityRemark.setEditBy(user.getId());
            returnObject.setCode(Contants.RETURNOBJECT_CODE_SUCCESS);
            returnObject.setReturnData(activityRemark);
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURNOBJECT_CODE_FAIL);
            returnObject.setMessage("更新失败");
        }
        return returnObject;
    }

    //删除activity_remark中的一条数据
    @RequestMapping("/workbench/activity/deleteActivityRemarkById.do")
    public @ResponseBody
    Object deleteActivityRemarkById(String id) {
        ReturnObject returnObject = new ReturnObject();


        try {
            int result = activityRemarkService.deleteActivityRemarkById(id);
            if (result > 0) {
                returnObject.setCode(Contants.RETURNOBJECT_CODE_SUCCESS);
            } else {
                returnObject.setCode(Contants.RETURNOBJECT_CODE_FAIL);
                returnObject.setMessage("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURNOBJECT_CODE_FAIL);
            returnObject.setMessage("删除失败");

        }
        return returnObject;
    }

}
