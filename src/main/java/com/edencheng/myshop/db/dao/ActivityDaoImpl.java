package com.edencheng.myshop.db.dao;

import com.edencheng.myshop.db.mappers.ActivityMapper;
import com.edencheng.myshop.db.po.Activity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
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

    @Override
    public boolean lockStock(Long activityId) {
        int result = activityMapper.lockStock(activityId);
        if(result < 1){
            log.error("Lock stock failed");
            return false;
        }
        return true;
    }

    @Override
    public boolean deductStock(Long activityId){
        int result = activityMapper.deductStock(activityId);
        if(result < 1){
            log.error("Deduct stock failed");
            return false;
        }
        return true;
    }
}
