package com.s1gn.stock.mapper;

import com.s1gn.stock.pojo.entity.StockBusiness;

import java.util.List;

/**
* @author zzy
* @description 针对表【stock_business(主营业务表)】的数据库操作Mapper
* @createDate 2024-03-20 15:13:40
* @Entity com.s1gn.stock.pojo.entity.StockBusiness
*/
public interface StockBusinessMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockBusiness record);

    int insertSelective(StockBusiness record);

    StockBusiness selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockBusiness record);

    int updateByPrimaryKey(StockBusiness record);
    /**
     * @Auther s1gn
     * @Description 获取所有股票代码
     * @Date 2024/4/4 15:45
     * @return {@link List<String> }
     **/
    List<String> getAllStockCodes();

}
