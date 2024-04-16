package com.s1gn.stock.mapper;

import com.s1gn.stock.pojo.entity.StockOuterMarketIndexInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @author zzy
* @description 针对表【stock_outer_market_index_info(外盘详情信息表)】的数据库操作Mapper
* @createDate 2024-03-20 15:13:40
* @Entity com.s1gn.stock.pojo.entity.StockOuterMarketIndexInfo
*/
public interface StockOuterMarketIndexInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockOuterMarketIndexInfo record);

    int insertSelective(StockOuterMarketIndexInfo record);

    StockOuterMarketIndexInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockOuterMarketIndexInfo record);

    int updateByPrimaryKey(StockOuterMarketIndexInfo record);

    /**
     * @Auther s1gn
     * @Description 根据时间范围获取外盘指数信息，开始时间和结束时间内，按照时间、点数降序排列，返回前4条
     * @Date 2024/4/11 15:01
     * @param startDate
     * @param endDate
     * @return {@link List< Map> }
     **/
    List<Map<String, String>> getExternalIndexInfo(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
    /**
     * @Auther s1gn
     * @Description 批量插入外盘指数信息
     * @Date 2024/4/15 22:20
     * @param entities
     * @return {@link int }
     **/
    int insertBatch(@Param("entities") List<StockOuterMarketIndexInfo> entities);
}
