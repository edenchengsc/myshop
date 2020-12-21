package com.edencheng.myshop.db.dao;

import com.edencheng.myshop.db.po.Commodity;

public interface CommodityDao {
    public Commodity queryCommodityById(Long commodityId);
}
