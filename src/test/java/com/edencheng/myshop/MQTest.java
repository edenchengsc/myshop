package com.edencheng.myshop;

import com.edencheng.myshop.mq.RocketMQService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class MQTest {

    @Autowired
    RocketMQService rocketMQService;

    @Test
    public void sendMQTest() throws Exception{
        rocketMQService.sendMessage("test-edencheng", "hello mq" + new Date().toString());
    }
}
