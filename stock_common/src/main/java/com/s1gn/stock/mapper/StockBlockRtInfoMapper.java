package com.s1gn.stock.mapper;

import com.s1gn.stock.pojo.domain.StockBlockDomain;
import com.s1gn.stock.pojo.domain.StockUpdownDomain;
import com.s1gn.stock.pojo.entity.StockBlockRtInfo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @author zzy
* @description 针对表【stock_block_rt_info(股票板块详情信息表)】的数据库操作Mapper
* @createDate 2024-03-20 15:13:40
* @Entity com.s1gn.stock.pojo.entity.StockBlockRtInfo
*/
public interface StockBlockRtInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockBlockRtInfo record);

    int insertSelective(StockBlockRtInfo record);

    StockBlockRtInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockBlockRtInfo record);

    int updateByPrimaryKey(StockBlockRtInfo record);

    List<StockBlockDomain> sectorAllLimit(@Param("curDate") Date curDate);

    List<StockUpdownDomain> getSockInfoByTime(@Param("curDate")Date curDate);

    List<StockUpdownDomain> getStockIncreaseInfo(@Param("curDate")Date curDate);
    /**
     * @Auther s1gn
     * @Description 获取股票涨跌停数量
     * @Date 2024/3/26 20:37
     * @param openDate
     * @param curDate
     * @param flag   1-涨停 0-跌停
     * @return {@link List<Map> }
     **/
    @MapKey("time")
    List<Map> getStockUpDownCount(@Param("startDate") Date openDate,
                                  @Param("endDate")Date curDate,
                                  @Param("flag") int flag);

    List<Map> getIncreaseRangeInfoByDate(@Param("curDate")Date curDate);
}
