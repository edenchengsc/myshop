package com.edencheng.myshop.service;

import com.edencheng.myshop.db.dao.ActivityDao;
import com.edencheng.myshop.db.po.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OverSellService {

    @Autowired
    private ActivityDao activityDao;

//    public String processOnsale(long activityId){
//        Activity activity = activityDao.queryActivitysById(activityId);
//        long availableStock = activity.getAvailableStock();
//        String result;
//        if(availableStock > 0){
//            result = "Congratulation, you got it!";
//            System.out.println(result);
//            availableStock = availableStock - 1;
//            activity.setAvailableStock(new Integer("" + availableStock));
//            activityDao.updateActivity(activity);
//        } else {
//            result = "Sorry, out of stock!";
//            System.out.println(result);
//        }
//
//        return result;
//    }



}
