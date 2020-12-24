package com.edencheng.myshop.service;

import com.alibaba.fastjson.JSON;
import com.edencheng.myshop.db.dao.ActivityDao;
import com.edencheng.myshop.db.dao.CommodityDao;
import com.edencheng.myshop.db.dao.OrderDao;
import com.edencheng.myshop.db.po.Activity;
import com.edencheng.myshop.db.po.Commodity;
import com.edencheng.myshop.db.po.Order;
import com.edencheng.myshop.mq.RocketMQService;
import com.edencheng.myshop.util.RedisService;
import com.edencheng.myshop.util.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class ActivityService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private CommodityDao commodityDao;

    @Autowired
    private RocketMQService rocketMQService;

    @Autowired
    private OrderDao orderDao;

    private SnowFlake snowFlake = new SnowFlake(1,1);

    public Order createOrder(long activityId, long userId) throws Exception{

        Activity activity = activityDao.queryActivitysById(activityId);
        Order order = new Order();
        order.setOrderNo(String.valueOf(snowFlake.nextId()));
        order.setActivityId(activity.getId());
        order.setUserId(userId);
        order.setOrderAmount(activity.getOnsalePrice().longValue());

        rocketMQService.sendMessage("onsale_order", JSON.toJSONString(order));

        //messageDelaylevel: 1s 5s 10s 30s 1m 2m 3m ....
        rocketMQService.sendDelayMessage("pay_check", JSON.toJSONString(order),4); // delay timeLevel 2:  5s
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

    public void payOrderProcess(String orderNo) throws Exception {
        //deduct stock: avai - 1, lock - 1
        //redis
        log.info("Complete payment, orderNo:" + orderNo);
        Order order = orderDao.queryOrder(orderNo);

        if(order == null){
            log.error("Order number does not exists: " + orderNo);
            return;
        } else if(order.getOrderStatus() != 1){
            log.error("Invalid order: " + orderNo);
            return;
        }

        boolean deductStockResult = activityDao.deductStock(order.getActivityId());
        if(deductStockResult){
            order.setPayTime(new Date());
            order.setOrderStatus(2);
            orderDao.updateOrder(order);

            rocketMQService.sendMessage("pay_done", JSON.toJSONString(order));
        }

    }

    public void pushInfoToRedis(long activityId){
        Activity activity = activityDao.queryActivitysById(activityId);
        redisService.setValue("activity:" + activityId, JSON.toJSONString(activity));
        Commodity commodity = commodityDao.queryCommodityById(activity.getCommodityId());
        redisService.setValue("commodity:" + activity.getCommodityId(), JSON.toJSONString(commodity));
    }
}
