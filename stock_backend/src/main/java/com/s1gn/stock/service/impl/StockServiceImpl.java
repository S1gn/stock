package com.s1gn.stock.service.impl;

import com.alibaba.excel.EasyExcel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.s1gn.stock.mapper.StockBlockRtInfoMapper;
import com.s1gn.stock.mapper.StockMarketIndexInfoMapper;
import com.s1gn.stock.mapper.StockRtInfoMapper;
import com.s1gn.stock.pojo.domain.*;
import com.s1gn.stock.pojo.vo.StockInfoConfig;
import com.s1gn.stock.service.StockService;
import com.s1gn.stock.utils.DateTimeUtil;
import com.s1gn.stock.vo.resp.PageResult;
import com.s1gn.stock.vo.resp.R;
import com.s1gn.stock.vo.resp.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * @ClassName StockServiceImpl
 * @Description 股票服务实现类
 * @Author S1gn
 * @Date 21:34
 * @Version 1.0
 */
@Service("stockService")
@Slf4j
public class StockServiceImpl implements StockService {

    @Autowired
    private StockInfoConfig stockInfoConfig;

    @Autowired
    private StockMarketIndexInfoMapper stockMarketIndexInfoMapper;

    @Autowired
    private StockBlockRtInfoMapper stockBlockRtInfoMapper;

    @Autowired
    private StockRtInfoMapper stockRtInfoMapper;

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

    @Override
    public void exportStockUpDownInfo(Integer page, Integer pageSize, HttpServletResponse response) {
        //获取分页数据
        R<PageResult<StockUpdownDomain>> stockInfoByPage = getSockInfoByPage(page, pageSize);
        List<StockUpdownDomain> rows = stockInfoByPage.getData().getRows();
        //导出数据
        //设置响应excel文件格式类型
        response.setContentType("application/vnd.ms-excel");
        //2.设置响应数据的编码格式
        response.setCharacterEncoding("utf-8");
        //3.设置默认的文件名称
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        try {
            String fileName = URLEncoder.encode("股票信息表", "UTF-8");
            //设置默认文件名称：兼容一些特殊浏览器
            response.setHeader("content-disposition", "attachment;filename=" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), StockUpdownDomain.class).sheet("股票涨跌数据").doWrite(rows);
        } catch (IOException e) {
            log.error("导出股票涨跌数据失败,当前页码:{},每页条数:{}, 当前时间:{}", page, pageSize, DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            R<Object> error = R.error(ResponseCode.ERROR);
            try {
                String jsonData = new ObjectMapper().writeValueAsString(error);
                response.getWriter().write(jsonData);
            } catch (IOException ex) {
                log.error("响应数据失败,当前时间:{}", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            }
        }
    }

    @Override
    public R<Map<String, List>> getCompareStockTradeAmt() {
        // 获取T日开盘点和最新时间
        DateTime tEndDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        Date tEndDate = tEndDateTime.toDate();
        Date tstartDate = DateTimeUtil.getOpenDate(tEndDateTime).toDate();
        //TODO  mock数据
        tstartDate=DateTime.parse("2022-01-03 09:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        tEndDate=DateTime.parse("2022-01-03 14:40:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        // 获取T-1日开盘点和最新时间
        DateTime preTEndDateTime = DateTimeUtil.getPreviousTradingDay(tEndDateTime);
        Date preTEndDate = preTEndDateTime.toDate();
        Date preTStartDate = DateTimeUtil.getOpenDate(preTEndDateTime).toDate();
        //TODO  mock数据
        preTStartDate=DateTime.parse("2022-01-02 09:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        preTEndDate=DateTime.parse("2022-01-02 14:40:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        // 获取T日和T-1日的每分钟交易量
        List<Map> tData = stockMarketIndexInfoMapper.getSumAmtInfo(tstartDate, tEndDate, stockInfoConfig.getInner());
        List<Map> preTData = stockMarketIndexInfoMapper.getSumAmtInfo(preTStartDate, preTEndDate, stockInfoConfig.getInner());
        // 封装返回结果
        HashMap<String, List> info = new HashMap<String, List>();
        info.put("amtList", tData);
        info.put("yesAmtlist", preTData);
        return R.ok(info);
    }

    @Override
    public R<Map> getIncreaseRangeInfo() {
        // 获取最新交易时间，精确到分钟，秒和毫秒为0
        DateTime curDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        Date curDate = curDateTime.toDate();
        curDate = DateTime.parse("2022-01-06 9:55:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        // 获取当日股票涨幅区间统计数量
        List<Map> data = stockBlockRtInfoMapper.getIncreaseRangeInfoByDate(curDate);
        // 优化排序结果
        List<String> upDownRange = stockInfoConfig.getUpDownRange();
        List<Map> result = new ArrayList<>();
        for (String str : upDownRange) {
            Map map = null;
            for (Map item : data) {
                if (item.containsValue(str)) {
                    map = item;
                    break;
                }
            }
            if(map==null){
                map = new HashMap();
                map.put("title", str);
                map.put("count", 0);
            }
            result.add(map);
        }

        // 封装返回结果
        HashMap<String, Object> info = new HashMap<String, Object>();
        info.put("time", curDateTime.toString("yyyy-MM-dd HH:mm:ss"));
        info.put("infos", result);
        return R.ok(info);
    }

    @Override
    public R<List<Stock4MinuteDomain>> getStockScreenTimeSharing(String stockCode) {
        // 获取开盘时间和当前时间
        DateTime endDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        Date endDate = endDateTime.toDate();
        endDate=DateTime.parse("2021-12-30 14:47:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        Date openDate = DateTimeUtil.getOpenDate(endDateTime).toDate();
        openDate=DateTime.parse("2021-12-30 09:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        // 查询
        List<Stock4MinuteDomain> data = stockRtInfoMapper.getStock4MinuteInfo(openDate, endDate, stockCode);
        return R.ok(data);
    }

    @Override
    public R<List<Stock4DayDomain>> getStockScreenDKline(String stockCode) {
        // 获取起始时间和结束时间
        DateTime endDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        Date endDate = endDateTime.toDate();
        endDate=DateTime.parse("2022-06-06 14:25:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        DateTime startDateTime = endDateTime.minusMonths(3);
        Date startDate=DateTime.parse("2022-01-01 09:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        // 查询
        List<Stock4DayDomain> data = stockRtInfoMapper.getStock4DayInfo(startDate, endDate, stockCode);
        return R.ok(data);
    }
}
