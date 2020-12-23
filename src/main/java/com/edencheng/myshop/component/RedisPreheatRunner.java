package com.edencheng.myshop.component;

import com.edencheng.myshop.db.dao.ActivityDao;
import com.edencheng.myshop.db.po.Activity;
import com.edencheng.myshop.util.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RedisPreheatRunner implements ApplicationRunner {

    @Autowired
    RedisService redisService;

    @Autowired
    ActivityDao activityDao;

    @Override
    public void run(ApplicationArguments args) throws Exception{
        List<Activity> activities = activityDao.queryActivitysByStatus(1);
        for(Activity activity:activities){
            redisService.setValue("stock:" + activity.getId(), (long) activity.getAvailableStock());
        }
    }
}
