package com.s1gn.stock.controller;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.server.HttpServerResponse;
import com.s1gn.stock.pojo.domain.InnerMarketDomain;
import com.s1gn.stock.pojo.domain.StockBlockDomain;
import com.s1gn.stock.pojo.domain.StockUpdownDomain;
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

    @GetMapping("/index/sector/all")
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
}
