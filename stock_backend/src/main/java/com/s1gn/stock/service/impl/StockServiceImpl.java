package com.s1gn.stock.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.s1gn.stock.mapper.StockBlockRtInfoMapper;
import com.s1gn.stock.mapper.StockMarketIndexInfoMapper;
import com.s1gn.stock.mapper.StockRtInfoMapper;
import com.s1gn.stock.pojo.domain.InnerMarketDomain;
import com.s1gn.stock.pojo.domain.StockBlockDomain;
import com.s1gn.stock.pojo.domain.StockUpdownDomain;
import com.s1gn.stock.pojo.vo.StockInfoConfig;
import com.s1gn.stock.service.StockService;
import com.s1gn.stock.utils.DateTimeUtil;
import com.s1gn.stock.vo.resp.PageResult;
import com.s1gn.stock.vo.resp.R;
import lombok.val;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    /**
     * @Auther s1gn
     * @Description 分页查询最新股票涨跌幅数据
     * @Date 2024/3/26 16:35
     * @param page
     * @param pageSize
     * @return {@link R< PageResult< StockUpdownDomain>> }
     **/
    @Override
    public R<PageResult<StockUpdownDomain>> getSockInfoByPage(Integer page, Integer pageSize) {
        // 获取最新交易时间，精确到分钟，秒和毫秒为0
        DateTime curDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        Date curDate = curDateTime.toDate();
        curDate = DateTime.parse("2021-12-30 09:42:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        // 设置分页参数
        PageHelper.startPage(page, pageSize);
        // mapper查询数据
        List<StockUpdownDomain> data = stockBlockRtInfoMapper.getSockInfoByTime(curDate);
        // 封装返回结果
        PageInfo<StockUpdownDomain> pageInfo = new PageInfo<>(data);
        PageResult<StockUpdownDomain> pageResult = new PageResult<>(pageInfo);
        return R.ok(pageResult);
    }

    /**
     * @Auther s1gn
     * @Description 获取股票涨幅最高的前4条数据
     * @Date 2024/3/26 16:53
     * @return {@link R< List< StockUpdownDomain>> }
     **/
    @Override
    public R<List<StockUpdownDomain>> getStockIncreaseInfo() {
        // 获取最新交易时间，精确到分钟，秒和毫秒为0
        DateTime curDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        Date curDate = curDateTime.toDate();
        curDate = DateTime.parse("2021-12-30 09:42:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        // mapper查询数据
        List<StockUpdownDomain> data = stockBlockRtInfoMapper.getStockIncreaseInfo(curDate);
        // 封装返回结果
        return R.ok(data);
    }

    @Override
    public R<Map<String, List>> getStockUpDownCount() {
        // 获取最新交易时间与开盘时间
        DateTime curDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        Date curTime = curDateTime.toDate();
        curTime= DateTime.parse("2022-01-06 14:25:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //1.2 获取最新交易时间对应的开盘时间
        DateTime openDate = DateTimeUtil.getOpenDate(curDateTime);
        Date openTime = openDate.toDate();
        openTime= DateTime.parse("2022-01-06 09:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        // 统计涨停数据
        List<Map> upCount = stockBlockRtInfoMapper.getStockUpDownCount(openTime, curTime, 1);
        // 统计跌停数据
        List<Map> downCount = stockBlockRtInfoMapper.getStockUpDownCount(openTime, curTime, 0);
        // 封装返回结果
        HashMap<String, List> info = new HashMap<String, List>();
        info.put("upList", upCount);
        info.put("downList", downCount);
        return R.ok(info);
    }
}
