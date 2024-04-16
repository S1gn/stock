package com.s1gn.stock.mapper;

import com.s1gn.stock.pojo.entity.StockBusiness;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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
    /**
     * @Auther s1gn
     * @Description 根据股票代码获取相似股票
     * @Date 2024/4/12 15:38
     * @param searchStr
     * @return {@link List< Map< String, String>> }
     **/
    List<Map<String, String>> getSimilarStockByCode(@Param("searchStr") String searchStr);
    /**
     * @Auther s1gn
     * @Description 根据股票代码获取股票描述
     * @Date 2024/4/12 15:44
     * @param stockCode
     * @return {@link List< Map< String, String>> }
     **/
    List<Map<String, String>> getStockDescribeByCode(@Param("stockCode") String stockCode);
}
