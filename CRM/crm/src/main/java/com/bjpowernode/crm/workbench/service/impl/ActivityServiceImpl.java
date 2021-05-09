package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.mapper.ActivityMapper;
import com.bjpowernode.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 姜宝
 * 2021/4/17
 */
@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityMapper activityMapper;

    @Override
    public int insertActivity(Activity activity) {
        return activityMapper.insertActivity(activity);
    }

    @Override
    public List<Activity> selectActivityForPageByCondition(Map<String, Object> map) {
        return activityMapper.selectActivityForPageByCondition(map);
    }

    @Override
    public long selectCountOfActivityByCondition(Map<String, Object> map) {
        return activityMapper.selectCountOfActivityByCondition(map);
    }

    @Override
    public Activity selectActivityById(String id) {
        return activityMapper.selectActivityById(id);
    }

    @Override
    public int updateActivity(Activity activity) {
        return activityMapper.updateActivity(activity);
    }

    @Override
    public int deleteActivityByIds(String[] ids) {
        return activityMapper.deleteActivityByIds(ids);
    }

    @Override
    public List<Activity> selectAllActivityForDetail() {
        return activityMapper.selectAllActivityForDetail();
    }

    @Override
    public List<Activity> selectActivityForDetailByIds(String[] ids) {
        return activityMapper.selectActivityForDetailByIds(ids);
    }

    @Override
    public int insertActivityByList(List<Activity> activityList) {
        return activityMapper.insertActivityByList(activityList);
    }

    @Override
    public Activity selectActivityForDetailById(String id) {
        return activityMapper.selectActivityForDetailById(id);
    }

    @Override
    public List<Activity> selectActivityForDetailByClueId(String clueId) {
        return activityMapper.selectActivityForDetailByClueId(clueId);
    }

    @Override
    public List<Activity> selectActivityForDetailByNameClueId(Map<String, Object> map) {
        return activityMapper.selectActivityForDetailByNameClueId(map);
    }

    @Override
    public List<Activity> selectActivityForDetailByName(String name) {
        return activityMapper.selectActivityForDetailByName(name);
    }

    @Override
    public List<Activity> searchActivityNoBoundById(Map<String, Object> map) {
        return activityMapper.searchActivityNoBoundById(map);
    }
}
