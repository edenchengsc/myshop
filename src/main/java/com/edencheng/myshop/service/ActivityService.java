package com.edencheng.myshop.service;

import com.edencheng.myshop.util.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {

    @Autowired
    private RedisService redisService;

    /**
     * Check if there is stock
     * @param activityId
     * @return
     */

    public boolean stockValidator(long activityId){
        String key = "stock:" + activityId;
        return redisService.stockDeductValidator(key);
    }
}
