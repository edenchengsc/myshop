package com.edencheng.myshop.db.mappers;

import com.edencheng.myshop.db.po.Activity;

import java.util.List;

public interface ActivityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Activity record);

    int insertSelective(Activity record);

    Activity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Activity record);

    int updateByPrimaryKey(Activity record);

    List<Activity> queryActivitysByStatus(int activityStatus);

    void insertActivity(Activity activity);

    int lockStock(Long activityId);

    int deductStock(Long id);

    void revertStock(Long activityId);
}