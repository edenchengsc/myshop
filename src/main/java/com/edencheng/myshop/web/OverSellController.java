package com.edencheng.myshop.web;

import com.edencheng.myshop.service.ActivityService;
import com.edencheng.myshop.service.OverSellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OverSellController {

    @Autowired
    private OverSellService overSellService;

    @Autowired
    private ActivityService activityService;


    @ResponseBody
    @RequestMapping("/processOnsale/{activityId}")
    public String processOnsale(@PathVariable long activityId){
        boolean stockValidateResult = activityService.stockValidator(activityId);

        return stockValidateResult?"Congratulations, you got it! " : "Sold out, try next time!";
    }


}
