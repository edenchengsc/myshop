package com.edencheng.myshop.mq;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
@RocketMQMessageListener(topic = "test-edencheng", consumerGroup = "consumerGroup-edencheng")
public class ConsumerListener implements RocketMQListener<MessageExt> {

    @Override
    public void onMessage(MessageExt messageExt){
        try{
            String body = new String(messageExt.getBody(), "UTF-8");
            System.out.println("Received message:" + body);
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }
}
