package com.edencheng.myshop.mq;

import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class RocketMQService {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * +
     * Send message
     *
     * @param topic
     * @param body
     * @throws Exception
     */
    public void sendMessage(String topic, String body) throws Exception {
        Message message = new Message(topic, body.getBytes());
        rocketMQTemplate.getProducer().send(message);
    }


    /**
     * send delay message
     * @param topic
     * @param body
     * @param delayTimeLevel
     * @throws Exception
     */
    public void sendDelayMessage(String topic, String body, int delayTimeLevel) throws Exception{
        Message message = new Message(topic, body.getBytes());
        message.setDelayTimeLevel(delayTimeLevel);
        rocketMQTemplate.getProducer().send(message);
    }
}