package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.ActivityRemark;

import java.util.List;

/**
 * 姜宝
 * 2021/4/24
 */
public interface ActivityRemarkService {
    /**
     * 根据activityId查询该市场活动下所有的备注明细信息
     */
    List<ActivityRemark> selectActivityRemarkForDetailByActivityId(String activityId);

    /**
     * 保存创建的市场活动备注
     */
    int insertActivityRemark(ActivityRemark remark);

    /**
     * 根据id删除市场活动备注
     */
    int deleteActivityRemarkById(String id);

    /**
     * 保存修改的市场活动备注
     */
    int updateActivityRemark(ActivityRemark remark);

    ActivityRemark selectByPrimaryKey(String id);
}
