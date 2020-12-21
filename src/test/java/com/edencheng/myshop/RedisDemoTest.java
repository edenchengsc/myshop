package com.edencheng.myshop;

import com.edencheng.myshop.util.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class RedisDemoTest {

    @Resource
    private RedisService redisService;

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

}


