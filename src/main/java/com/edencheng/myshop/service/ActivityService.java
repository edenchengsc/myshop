package com.edencheng.myshop.service;

import com.alibaba.fastjson.JSON;
import com.edencheng.myshop.db.dao.ActivityDao;
import com.edencheng.myshop.db.po.Activity;
import com.edencheng.myshop.db.po.Order;
import com.edencheng.myshop.mq.RocketMQService;
import com.edencheng.myshop.util.RedisService;
import com.edencheng.myshop.util.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private RocketMQService rocketMQService;

    private SnowFlake snowFlake = new SnowFlake(1,1);

    public Order createOrder(long activityId, long userId) throws Exception{

        Activity activity = activityDao.queryActivitysById(activityId);
        Order order = new Order();
        order.setOrderNo(String.valueOf(snowFlake.nextId()));
        order.setActivityId(activity.getId());
        order.setUserId(userId);
        order.setOrderAmount(activity.getOnsalePrice().longValue());

        rocketMQService.sendMessage("onsale_order", JSON.toJSONString(order));

        return order;
    }
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
