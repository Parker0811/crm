package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

/**
 * 姜宝
 * 2021/4/17
 */
public interface ActivityService {
    /**
     * 保存创建的市场活动
     */
    int insertActivity(Activity activity);

    /**
     * 根据条件分页查询市场活动
     */
    List<Activity> selectActivityForPageByCondition(Map<String, Object> map);

    /**
     * 根据条件查询市场活动总条数
     */
    long selectCountOfActivityByCondition(Map<String, Object> map);
    /**
     * 根据id查询市场活动
     */
    Activity selectActivityById(String id);

    /**
     * 保存修改的市场活动
     */
    int updateActivity(Activity activity);

    /**
     * 根据ids批量删除市场活动
     */
    int deleteActivityByIds(String[] ids);

    /**
     * 查询所有的市场活动
     */
    List<Activity> selectAllActivityForDetail();
    /**
     * 根据ids查询市场活动
     */
    List<Activity> selectActivityForDetailByIds(String[] ids);

    /**
     * 批量保存创建的市场活动
     */
    int insertActivityByList(List<Activity> activityList);

    /**
     * 根据id查询市场活动的明细信息
     */
    Activity selectActivityForDetailById(String id);

    /**
     * 根据clueId查询跟该线索相关联的市场活动明细信息
     */
    List<Activity> selectActivityForDetailByClueId(String clueId);

    /**
     * 根据name模糊查询市场活动，并且把已经跟clueId关联过的市场活动排除
     */
    List<Activity> selectActivityForDetailByNameClueId(Map<String, Object> map);

    /**
     * 根据name模糊查询市场活动
     */
    List<Activity> selectActivityForDetailByName(String name);

    List<Activity> searchActivityNoBoundById(Map<String, Object> map);
}
