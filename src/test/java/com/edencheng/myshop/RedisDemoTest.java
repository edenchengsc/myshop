package com.edencheng.myshop;

import com.edencheng.myshop.service.ActivityService;
import com.edencheng.myshop.util.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.UUID;

@SpringBootTest
public class RedisDemoTest {

    @Resource
    private RedisService redisService;

    @Resource
    private ActivityService activityService;

    @Test
    public void stockTest(){
        redisService.setValue("stock:32", 10L);
    }

    @Test
    public void getStockTest(){
        String stock = redisService.getValue("stock:32");
        System.out.println(stock);
    }

    @Test
    public void stockDeductValidatorTest(){
        boolean result = redisService.stockDeductValidator("stock:32");
        System.out.println("result: " + result);
        String stock = redisService.getValue("stock:32");
        System.out.println("stock:" + stock);

    }

    @Test
    public void pushInfoToRedisTest(){
        activityService.pushInfoToRedis(33);
    }

    @Test
    public void getInfoToRedisTest(){
        String activtityInfo = redisService.getValue("activity:" + 33);
        System.out.println(activtityInfo);
        String commodityInfo = redisService.getValue("commodity:" + 3);
        System.out.println(commodityInfo);
    }

    @Test
    public void testConcurrentAddLock(){
        for(int i = 0; i < 10; i++){
            String requestId = UUID.randomUUID().toString();
            System.out.println(redisService.tryGetDistributedLock("A", requestId, 1000));
        }
    }

}


