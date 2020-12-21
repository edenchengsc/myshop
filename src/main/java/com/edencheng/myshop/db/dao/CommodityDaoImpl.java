package com.edencheng.myshop.db.dao;

import com.edencheng.myshop.db.mappers.CommodityMapper;
import com.edencheng.myshop.db.po.Commodity;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class CommodityDaoImpl implements CommodityDao {

    @Resource
    private CommodityMapper commodityMapper;

    public Commodity queryCommodityById(Long commodityId){
        return commodityMapper.selectByPrimaryKey(commodityId);
    }

}
