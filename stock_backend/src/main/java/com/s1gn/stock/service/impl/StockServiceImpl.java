package com.s1gn.stock.service.impl;

import com.s1gn.stock.mapper.StockBlockRtInfoMapper;
import com.s1gn.stock.mapper.StockMarketIndexInfoMapper;
import com.s1gn.stock.pojo.domain.InnerMarketDomain;
import com.s1gn.stock.pojo.domain.StockBlockDomain;
import com.s1gn.stock.pojo.vo.StockInfoConfig;
import com.s1gn.stock.service.StockService;
import com.s1gn.stock.utils.DateTimeUtil;
import com.s1gn.stock.vo.resp.R;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ClassName StockServiceImpl
 * @Description 股票服务实现类
 * @Author S1gn
 * @Date 21:34
 * @Version 1.0
 */
@Service("stockService")
public class StockServiceImpl implements StockService {

    @Autowired
    private StockInfoConfig stockInfoConfig;

    @Autowired
    private StockMarketIndexInfoMapper stockMarketIndexInfoMapper;

    @Autowired
    private StockBlockRtInfoMapper stockBlockRtInfoMapper;
    /**
     * @Auther s1gn
     * @Description 获取国内大盘最新数据
     * @Date 2024/3/25 21:35
     * @Param
     * @Return * @return {@link R< List< InnerMarketDomain>> }
     **/

    @Override
    public R<List<InnerMarketDomain>> getInnerMarketInfo() {
        // 获取最新交易时间，精确到分钟，秒和毫秒为0
        DateTime curDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        Date curDate = curDateTime.toDate();
        curDate = DateTime.parse("2022-01-02 09:32:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        // 获取大盘编码集合
        List<String> innerCode = stockInfoConfig.getInner();
        // 调用mapper查询大盘数据
        List<InnerMarketDomain> data = stockMarketIndexInfoMapper.getMarketInfo(curDate, innerCode);
        // 封装返回结果
        return R.ok(data);
    }

    /**
     * @Auther s1gn
     * @Description 查询沪深两市最新的板块行情数据，并按照交易金额降序排序展示前10条记录
     * @Date 2024/3/25 22:24
     * @Param
     * @Return * @return {@link R< List< StockBlockDomain>> }
     **/
    @Override
    public R<List<StockBlockDomain>> sectorAllLimit() {
        // 获取最新交易时间，精确到分钟，秒和毫秒为0
        DateTime curDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        Date curDate = curDateTime.toDate();
        curDate = DateTime.parse("2021-12-21 14:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        // 调用mapper查询大盘数据
        List<StockBlockDomain> data = stockBlockRtInfoMapper.sectorAllLimit(curDate);
        // 封装返回结果
        return R.ok(data);
    }
}
