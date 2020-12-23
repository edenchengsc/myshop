package com.edencheng.myshop.db.dao;


import com.edencheng.myshop.db.po.Activity;

import java.util.List;

public interface ActivityDao {

    public boolean lockStock(long activityId);

    public List<Activity> queryActivitysByStatus(int activityStatus);

    public void insertActivity(Activity activity);

    public Activity queryActivitysById(long activityId);

    public void updateActivity(Activity activity);
}
