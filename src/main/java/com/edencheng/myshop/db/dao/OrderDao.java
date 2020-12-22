package com.edencheng.myshop.db.dao;

import com.edencheng.myshop.db.po.Order;

public interface OrderDao {

    void insertOrder(Order order);

    Order queryOrder(String orderNo);

    void updateOrder(Order order);
}
