package com.edencheng.myshop;

import com.edencheng.myshop.db.dao.ActivityDao;
import com.edencheng.myshop.db.mappers.ActivityMapper;
import com.edencheng.myshop.db.po.Activity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
public class DaoTest {

    @Resource
    private ActivityMapper activityMapper;

    @Autowired
    private ActivityDao activityDao;


    //what's different the two tags

    @Test
    void ActivityTest(){
        Activity activity = new Activity();
        activity.setName("testing");
        activity.setCommodityId(999L);   //why L
        activity.setTotalStock(100L);
        activity.setOnsalePrice(new BigDecimal(99));
        activity.setActivityStatus(16);
        activity.setOriginalPrice(new BigDecimal(99));
        activity.setAvailableStock(100);
        activity.setLockStock(0L);
        activityMapper.insert(activity);
        System.out.println("============>" + activityMapper.selectByPrimaryKey(1L));
    }

    @Test
    void setSeckillActivityQuery(){
        List<Activity> activitys =  activityDao.queryActivitysByStatus(0);
        System.out.println(activitys.size());
        activitys.stream().forEach(activity ->System.out.println(activity.toString()));
    }

}
