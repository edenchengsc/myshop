package com.edencheng.myshop.mq;

import com.alibaba.fastjson.JSON;
import com.edencheng.myshop.db.dao.ActivityDao;
import com.edencheng.myshop.db.dao.OrderDao;
import com.edencheng.myshop.db.po.Order;
import com.edencheng.myshop.util.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RocketMQMessageListener(topic="pay_check", consumerGroup = "pay_check_group")
public class PayStatusCheckListener implements RocketMQListener<MessageExt>  {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private RedisService redisService;

    @Override
    public void onMessage(MessageExt messageExt){
           String message = new String(messageExt.getBody(), StandardCharsets.UTF_8);
           log.info("Received order pay status check message:" + message);
           Order order = JSON.parseObject(message, Order.class);
           Order orderInfo = orderDao.queryOrder(order.getOrderNo());
           if(orderInfo.getOrderStatus() != 2){
               log.info("Order wasn't paid, closing it! order number: " + orderInfo.getOrderNo());
               orderInfo.setOrderStatus(99);
               orderDao.updateOrder(orderInfo);
               activityDao.revertStock(order.getActivityId());
               redisService.revertStock("stock:" + order.getActivityId());
               redisService.removeFromLimitedMemberList(order.getActivityId(), order.getUserId());

           }

    }
}


