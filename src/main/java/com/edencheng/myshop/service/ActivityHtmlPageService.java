package com.edencheng.myshop.service;


import com.edencheng.myshop.db.dao.ActivityDao;
import com.edencheng.myshop.db.dao.CommodityDao;
import com.edencheng.myshop.db.po.Activity;
import com.edencheng.myshop.db.po.Commodity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class ActivityHtmlPageService {
    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private CommodityDao commodityDao;

    public void createActivityHtml(long activityId){

        PrintWriter writer = null;
        try {
            Activity activity = activityDao.queryActivitysById(activityId);
            Commodity commodity = commodityDao.queryCommodityById(activity.getCommodityId());
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("activity", activity);
            resultMap.put("commodity", commodity);
            resultMap.put("onsalePrice", activity.getOnsalePrice());
            resultMap.put("originalPrice", activity.getOriginalPrice());
            resultMap.put("commodityId", activity.getCommodityId());
            resultMap.put("commodityName", commodity.getCommodityName());
            resultMap.put("commodityDesc", commodity.getCommodityDesc());

            Context context = new Context();
            context.setVariables(resultMap);

            File file = new File("src/main/resources/templates" + "item_" + activityId + ".html");
            writer = new PrintWriter(file);

            templateEngine.process("_item", context, writer);
        }catch (Exception e) {
            log.error(e.toString());
            log.error("Generating   static content error" + activityId);
        } finally {
            if(writer != null){
                writer.close();
            }




        }
    }

}
