package com.edencheng.myshop.db.dao;

import com.edencheng.myshop.db.mappers.ActivityMapper;
import com.edencheng.myshop.db.po.Activity;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class ActivityDaoImpl implements ActivityDao {
    @Resource
    private ActivityMapper activityMapper;

    @Override
    public List<Activity> queryActivitysByStatus(int activityStatus) {
        return activityMapper.queryActivitysByStatus(activityStatus);
    }

    @Override
    public void insertActivity(Activity activity) {
        activityMapper.insert(activity);
    }

    @Override
    public Activity queryActivitysById(long activityId) {
        return activityMapper.selectByPrimaryKey(activityId);
    }

    @Override
    public void updateActivity(Activity activity){
        activityMapper.updateByPrimaryKeySelective(activity);
    }
}
