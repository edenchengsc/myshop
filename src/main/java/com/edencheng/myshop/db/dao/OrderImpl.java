package com.edencheng.myshop.db.dao;

import com.edencheng.myshop.db.mappers.OrderMapper;
import com.edencheng.myshop.db.po.Order;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class OrderImpl implements OrderDao{

    @Resource
    private OrderMapper orderMapper;

    @Override
    public void insertOrder(Order order){
        orderMapper.insert(order);
    }

    @Override
    public Order queryOrder(String orderNo){
        return orderMapper.selectByOrderNo(orderNo);
    }

    @Override
    public void updateOrder(Order order){
        orderMapper.updateByPrimaryKey(order);
    }
}
