package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.mapper.ActivityRemarkMapper;
import com.bjpowernode.crm.workbench.service.ActivityRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 姜宝
 * 2021/4/24
 */
@Service
public class ActivityRemarkServiceImpl  implements ActivityRemarkService {

    @Autowired
    private ActivityRemarkMapper activityRemarkMapper;

    @Override
    public List<ActivityRemark> selectActivityRemarkForDetailByActivityId(String activityId) {
        return activityRemarkMapper.selectActivityRemarkForDetailByActivityId(activityId);
    }

    @Override
    public int insertActivityRemark(ActivityRemark remark) {
        return activityRemarkMapper.insertActivityRemark(remark);
    }

    @Override
    public int deleteActivityRemarkById(String id) {
        return activityRemarkMapper.deleteActivityRemarkById(id);
    }

    @Override
    public int updateActivityRemark(ActivityRemark remark) {
        return activityRemarkMapper.updateActivityRemark(remark);
    }

    @Override
    public ActivityRemark selectByPrimaryKey(String id) {
        return activityRemarkMapper.selectByPrimaryKey(id);
    }
}
