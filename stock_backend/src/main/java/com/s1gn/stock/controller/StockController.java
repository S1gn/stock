package com.s1gn.stock.controller;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.server.HttpServerResponse;
import com.s1gn.stock.pojo.domain.*;
import com.s1gn.stock.service.StockService;
import com.s1gn.stock.vo.resp.PageResult;
import com.s1gn.stock.vo.resp.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @ClassName StockController
 * @Description 股票接口控制器
 * @Author S1gn
 * @Date 21:30
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/quot")
public class StockController {

    @Autowired
    private StockService stockService;
    /**
     * @Auther s1gn
     * @Description 获取国内大盘最新数据
     * @Date 2024/3/25 21:31
     * @Param
     * @Return * @return {@link R< List< InnerMarketDomain>> }
     **/

    @GetMapping("/index/all")
    public R<List<InnerMarketDomain>> getInnerMarketInfo(){
        return stockService.getInnerMarketInfo();
    }

    @GetMapping("/sector/all")
    public R<List<StockBlockDomain>> sectorAll(){
        return stockService.sectorAllLimit();
    }
    /**
     * @Auther s1gn
     * @Description 获取股票信息
     * @Date 2024/3/26 16:36
     * @param page
     * @param pageSize
     * @return {@link R< PageResult< StockUpdownDomain>> }
     **/
    @GetMapping("/stock/all")
    public R<PageResult<StockUpdownDomain>> getStockInfoByPage(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                                               @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize)
    {
        return stockService.getSockInfoByPage(page, pageSize);
    }
    /**
     * @Auther s1gn
     * @Description 获取股票涨幅最高的前4条数据
     * @Date 2024/3/26 16:52
     * @return {@link R< List< StockUpdownDomain>> }
     **/
    @GetMapping("/stock/increase")
    public R<List<StockUpdownDomain>> getStockIncreaseInfo(){
        return stockService.getStockIncreaseInfo();
    }
    /**
     * @Auther s1gn
     * @Description 获取股票涨停跌停数据
     * @Date 2024/3/26 20:27
     * @return {@link R< Map< String, List>> }
     **/
    @GetMapping("/stock/updown/count")
    public R<Map<String, List>> getStockUpDownCount(){
        return stockService.getStockUpDownCount();
    }
    /**
     * @Auther s1gn
     * @Description 导出指定页数的股票涨跌数据excel
     * @Date 2024/3/26 22:12
     * @param page
     * @param pageSize
     * @param response
     * @return void
     **/
    @GetMapping("/stock/export")
    public void exportStockUpDownInfo(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                      @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize,
                                      HttpServletResponse response){
        stockService.exportStockUpDownInfo(page, pageSize, response);
    }

    /**
     * @Auther s1gn
     * @Description 统计大盘T日和T-1日的每分钟交易量
     * @Date 2024/3/27 21:20
     * @return {@link R< Map< String, List>> }
     **/
    @GetMapping("/stock/tradeAmt")
    public R<Map<String, List>> getCompareStockTradeAmt(){
        return stockService.getCompareStockTradeAmt();
    }

    /**
     * @Auther s1gn
     * @Description 获取当日股票涨幅区间统计数量
     * @Date 2024/3/27 21:50
     * @return {@link R< Map> }
     **/
    @GetMapping("/stock/updown")
    public R<Map> getIncreaseRangeInfo()
    {
            return stockService.getIncreaseRangeInfo(); }
    /**
     * @Auther s1gn
     * @Description 获取指定股票的分时数据
     * @Date 2024/3/31 22:02
     * @param stockCode 股票编码
     * @return {@link R< List< Stock4MinuteDomain>> }
     **/
    @GetMapping("/stock/screen/time-sharing")
    public R<List<Stock4MinuteDomain>> getStockScreenTimeSharing(@RequestParam(value = "code", required = true) String stockCode){
        return stockService.getStockScreenTimeSharing(stockCode);
    }
    /**
     * @Auther s1gn
     * @Description 获取指定股票的日K线数据
     * @Date 2024/3/31 22:39
     * @param stockCode  股票编码
     * @return {@link R< List< Stock4DayDomain>> }
     **/
    @GetMapping("/stock/screen/dkline")
    public R<List<Stock4DayDomain>> getStockScreenDKline(@RequestParam(value = "code", required = true) String stockCode){
        return stockService.getStockScreenDKline(stockCode);
    }

    /**
     * @Auther s1gn
     * @Description 外盘指数行情数据查询，根据时间和大盘点数降序排序取前4
     * @Date 2024/4/11 14:39
     * @return {@link R< List< Map>> }
     **/
    @GetMapping("/external/index")
    public R<List<Map<String, String>>> getExternalIndex(){
        return stockService.getExternalIndex();
    }

    /**
     * @Auther s1gn
     * @Description 根据股票代母模糊查询股票集合
     * @Date 2024/4/12 15:25
     * @param searchStr
     * @return {@link R< List< Map< String, String>>> }
     **/
    @GetMapping("/stock/search")
    public R<List<Map<String, String>>> getSimilarStock(@RequestParam(value = "searchStr", required = true)String searchStr){
        return stockService.getSimilarStock(searchStr);
    }

    /**
     * @Auther s1gn
     * @Description 根据股票代码获取股票描述信息
     * @Date 2024/4/12 15:43
     * @param stockCode
     * @return {@link R< List< Map< String, String>>> }
     **/
    @GetMapping("/stock/describe")
    public R<List<Map<String, String>>> getStockDescribe(@RequestParam(value = "code", required = true)String stockCode){
        return stockService.getStockDescribe(stockCode);
    }

    /**
     * @Auther s1gn
     * @Description 获取股票周K线图数据
     * @Date 2024/4/12 15:55
     * @param stockCode
     * @return {@link R< List< Stock4WeekDomain>> }
     **/
    @GetMapping("/stock/screen/weekkline")
    public R<List<Stock4WeekDomain>> getStockScreenWeekKline(@RequestParam(value = "code", required = true) String stockCode){
        return stockService.getStockScreenWeekKline(stockCode);
    }

    /**
     * @Auther s1gn
     * @Description 获取股票秒级数据
     * @Date 2024/4/12 16:45
     * @param stockCode
     * @return {@link R< Stock4SecondDomain> }
     **/
    @GetMapping("/stock/screen/second/detail")
    public R<Stock4SecondDomain> getStockScreenSecondDetail(@RequestParam(value = "code", required = true) String stockCode){
        return stockService.getStockScreenSecondDetail(stockCode);
    }

    /**
     * @Auther s1gn
     * @Description 查询交易流水数据
     * @Date 2024/4/12 16:55
     * @param stockCode
     * @return {@link null }
     **/
    @GetMapping("/stock/screen/second")
    public R<List<Map<String, String>>> getStockScreenSecond(@RequestParam(value = "code", required = true) String stockCode){
        return stockService.getStockScreenSecond(stockCode);
    }

}
