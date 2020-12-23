package com.edencheng.myshop.mq;

import com.alibaba.fastjson.JSON;
import com.edencheng.myshop.db.dao.ActivityDao;
import com.edencheng.myshop.db.dao.OrderDao;
import com.edencheng.myshop.db.po.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
@RocketMQMessageListener(topic="onsale_order", consumerGroup = "onsale_order_group")
public class OrderConsumer implements RocketMQListener<MessageExt>{
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ActivityDao activityDao;

    @Override
    @Transactional
    public void onMessage(MessageExt messageExt){
        String message = new String(messageExt.getBody(), StandardCharsets.UTF_8);
        log.info("Received create order request: " + message);
        Order order = JSON.parseObject(message, Order.class);
        order.setCreateTime(new Date());

        boolean lockStockResult = activityDao.lockStock(order.getActivityId());
        if(lockStockResult){
            order.setOrderStatus(1);

        } else {
            order.setOrderStatus(0);
        }

        orderDao.insertOrder(order);
    }


}

