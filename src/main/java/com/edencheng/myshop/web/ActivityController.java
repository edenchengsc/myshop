package com.edencheng.myshop.web;

import com.edencheng.myshop.db.dao.ActivityDao;
import com.edencheng.myshop.db.dao.CommodityDao;
import com.edencheng.myshop.db.po.Activity;
import com.edencheng.myshop.db.po.Commodity;
import com.edencheng.myshop.util.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Controller
public class ActivityController {

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private CommodityDao commodityDao;

    @Resource
    private RedisService redisService;

    @RequestMapping("/addActivity")
    public String addActivity(){
        return "add_activity";
    }

    @RequestMapping("/addActivityAction")
    public String addActivityAction(
            @RequestParam("name") String name,
            @RequestParam("commodityId") long commodityId,
            @RequestParam("onSalePrice") BigDecimal onSalePrice,
            @RequestParam("originalPrice") BigDecimal originalPrice,
            @RequestParam("number") long number,
            @RequestParam("startTime") String startTime,
            @RequestParam("endTime") String endTime,
            Map<String, Object> resultMap
    ) throws ParseException {
        startTime = startTime.substring(0, 10) + startTime.substring(11);
        endTime = endTime.substring(0, 10) + endTime.substring(11);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-ddhh:mm");
        Activity activity = new Activity();
        activity.setName(name);
        activity.setCommodityId(commodityId);
        activity.setOnsalePrice(onSalePrice);
        activity.setOriginalPrice(originalPrice);
        activity.setTotalStock(number);
        activity.setAvailableStock(new Integer("" + number));
        activity.setLockStock(0L);
        activity.setActivityStatus(1);
        activity.setStartTime(format.parse(startTime));
        activity.setEndTime(format.parse(endTime));
        activityDao.insertActivity(activity);
        resultMap.put("activity", activity);
        return "add_success";
    }

    @RequestMapping("/onSales")
    public String activityList(Map<String, Object> resultMap) {
        List<Activity> activities =
                activityDao.queryActivitysByStatus(1);
        for(Activity activity : activities){
            redisService.setValue("stock:" + activity.getId(), (long) activity.getAvailableStock());
        }
        resultMap.put("activities", activities);
        return "activity";
    }

    @RequestMapping("/item/{activityId}")
    public String itemPage(Map<String, Object> resultMap, @PathVariable long activityId){
        Activity activity = activityDao.queryActivitysById(activityId);
        Commodity commodity = commodityDao.queryCommodityById(activity.getCommodityId());
        resultMap.put("activity", activity);
        resultMap.put("commodity", commodity);
        resultMap.put("onsalePrice", activity.getOnsalePrice());
        resultMap.put("originalPrice", activity.getOriginalPrice());
        resultMap.put("commodityId", commodity.getId());
        resultMap.put("commodityName", commodity.getCommodityName());
        resultMap.put("commodityDesc", commodity.getCommodityDesc());
        return "item";
    }
}
