package com.s1gn.stock.mapper;

import com.s1gn.stock.pojo.domain.InnerMarketDomain;
import com.s1gn.stock.pojo.entity.StockMarketIndexInfo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @author zzy
* @description 针对表【stock_market_index_info(国内大盘数据详情表)】的数据库操作Mapper
* @createDate 2024-03-20 15:13:40
* @Entity com.s1gn.stock.pojo.entity.StockMarketIndexInfo
*/
public interface StockMarketIndexInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockMarketIndexInfo record);

    int insertSelective(StockMarketIndexInfo record);

    StockMarketIndexInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockMarketIndexInfo record);

    int updateByPrimaryKey(StockMarketIndexInfo record);
    /**
     * @Auther s1gn
     * @Description 根据日期和编码获取大盘数据
     * @Date 2024/3/25 21:43
     * @Param * @param curDate
     * @param marketCodes
     * @Return * @return {@link List< InnerMarketDomain> }
     **/
    List<InnerMarketDomain> getMarketInfo(@Param("curDate") Date curDate, @Param("marketCodes") List<String> marketCodes);
    
    /**
     * @Auther s1gn
     * @Description 获取一段范围内的交易量
     * @Date 2024/3/27 21:28
     * @param tStartDate 开始时间
     * @param tEndDate  结束时间
     * @param inner  大盘码
     * @return {@link List< Map> }
     **/
    List<Map> getSumAmtInfo(@Param("openDate")Date tStartDate,
                            @Param("endDate")Date tEndDate,
                            @Param("marketCodes")List<String> inner);
}
