package com.s1gn.stock.mapper;

import com.s1gn.stock.pojo.domain.Stock4DayDomain;
import com.s1gn.stock.pojo.domain.Stock4MinuteDomain;
import com.s1gn.stock.pojo.entity.StockRtInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
* @author zzy
* @description 针对表【stock_rt_info(个股详情信息表)】的数据库操作Mapper
* @createDate 2024-03-20 15:13:40
* @Entity com.s1gn.stock.pojo.entity.StockRtInfo
*/
public interface StockRtInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockRtInfo record);

    int insertSelective(StockRtInfo record);

    StockRtInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockRtInfo record);

    int updateByPrimaryKey(StockRtInfo record);

    /**
     * @Auther s1gn
     * @Description 根据股票代码查询时间范围内股票的每分钟信息
     * @Date 2024/3/31 22:13
     * @param openDate 开盘时间
     * @param endDate  当前时间
     * @param stockCode   股票代码
     * @return {@link List< Stock4MinuteDomain> }
     **/
    List<Stock4MinuteDomain> getStock4MinuteInfo(@Param("openDate") Date openDate, @Param("endDate") Date endDate, @Param("stockCode") String stockCode);
    /**
     * @Auther s1gn
     * @Description 根据股票代码查询时间范围内股票的日K线信息
     * @Date 2024/3/31 22:44
     * @param startDate  开始时间
     * @param endDate  结束时间
     * @param stockCode   股票代码
     * @return {@link List< Stock4DayDomain> }
     **/
    List<Stock4DayDomain> getStock4DayInfo(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("stockCode") String stockCode);
    /**
     * @Auther s1gn
     * @Description 根据股票代码查询时间范围内每天的最后一次交易时间
     * @Date 2024/4/1 23:06
     * @param startDate
     * @param endDate
     * @param stockCode
     * @return {@link List< Date> }
     **/
    List<Date> getEveryDayLastTime(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("stockCode") String stockCode);
    /**
     * @Auther s1gn
     * @Description 根据指定的交易时间查询交易信息
     * @Date 2024/4/1 23:09
     * @param everyDayLastTime
     * @param stockCode
     * @return {@link List< Stock4DayDomain> }
     **/
    List<Stock4DayDomain> getStock4DayInfoByTimeList(@Param("timeList") List<Date> everyDayLastTime, @Param("stockCode") String stockCode);
}
