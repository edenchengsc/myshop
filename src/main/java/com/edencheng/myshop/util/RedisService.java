package com.edencheng.myshop.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collections;

@Slf4j
@Service
public class RedisService {

    @Autowired
    private JedisPool jedisPool;

    /**
     * set value
     * @param key
     * @param value
     */

    public void setValue(String key, Long value){
        Jedis jedisClient = jedisPool.getResource();
        jedisClient.set(key, value.toString());
    }

    public void setValue(String key, String value) {
        Jedis jedisClient = jedisPool.getResource();
        jedisClient.set(key, value);
    }

    /**
     * get value
     * @param key
     */
    public String getValue(String key){
        Jedis jedisClient = jedisPool.getResource();
        return jedisClient.get(key);
    }

    public boolean stockDeductValidator(String key){
        Jedis jedisClient = null;
        try{
            jedisClient = jedisPool.getResource();

            /**
             if (redis.call('exists',KEYS[1]) == 1)) then
             local stock = tonumber(redis.call('get', KEYS[1]));
             if( stock <=0 ) then
             return -1
             end;
             redis.call('decr',KEYS[1]);
             return stock - 1;
             end;
             return -1;
             */
            String script = "if redis.call('exists',KEYS[1]) == 1 then\n" +
                    "                 local stock = tonumber(redis.call('get', KEYS[1]))\n" +
                    "                 if( stock <=0 ) then\n" +
                    "                    return -1\n" +
                    "                 end;\n" +
                    "                 redis.call('decr',KEYS[1]);\n" +
                    "                 return stock - 1;\n" +
                    "             end;\n" +
                    "             return -1;";
            Long stock = (Long) jedisClient.eval(script, Collections.singletonList(key), Collections.emptyList());
            if(stock < 0){
                System.out.println("Out of stock");
                return false;
            } else {
                System.out.println("Congratulations, you got it!");
            }
            return true;
        } catch (Throwable throwable){
            System.out.println("deduct stock failed.  " + throwable.toString());
            return false;
        } finally {
            if(jedisClient != null ){
                jedisClient.close();
            }
        }
    }

    public void revertStock(String key) {
        Jedis jedis = jedisPool.getResource();
        jedis.incr(key);
        jedis.close();
    }

    public void addToLimitedMemberList(long activityId, long userId) {
        Jedis jedisClient = jedisPool.getResource();
        jedisClient.sadd("activity_users:" + activityId, String.valueOf(userId));
    }

    public boolean isInLimitedMemberList(long activityId, long userId) {
        Jedis jedisClient = jedisPool.getResource();
        boolean isLimitedUser = jedisClient.sismember("activity_users:" + activityId,String.valueOf(userId));
        log.info("userId:{}, activityId:{}, is in the limited member list {}: ", userId, activityId, isLimitedUser);
        return isLimitedUser;
    }

    public void removeFromLimitedMemberList(Long activityId, Long userId) {
        Jedis jedisClient = jedisPool.getResource();
        jedisClient.srem("activity_users:" + activityId, String.valueOf(userId));
    }
}
