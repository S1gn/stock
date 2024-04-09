package com.s1gn.stock.job;

import com.s1gn.stock.service.StockTimerTaskService;
import com.s1gn.stock.service.impl.StockTimerTaskServiceImpl;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName StockJob
 * @Description 定义xxl-job任务执行器bean
 * @Author S1gn
 * @Date 2024/4/7 23:06
 * @Version 1.0
 */
@Component
public class StockJob {

    @Autowired
    private StockTimerTaskService stockTimerTaskService;


    @XxlJob("stockJobHandler")
    public void stockJobHandler() {
        System.out.println("stockJobHandler");
    }
    
    /**
     * @Auther s1gn
     * @Description 定时采集A股大盘
     * @Date 2024/4/9 21:42
     * @return void
     **/
    @XxlJob("getInnerMarketInfo")
    public void getInnerMarketInfo() {
        stockTimerTaskService.getInnerMarketInfo();
    }

    /**
     * @Auther s1gn
     * @Description 定时采集A股各支股票的涨跌幅信息
     * @Date 2024/4/9 21:54
     * @return void
     **/
    @XxlJob("getInnerStockMarketInfo")
    public void getInnerStockMarketInfo() {
        stockTimerTaskService.getInnerStockMarketInfo();
    }
    /**
     * @Auther s1gn
     * @Description 定时采集A股板块信息
     * @Date 2024/4/9 21:55
     * @return void
     **/
    @XxlJob("getBlockInfo")
    public void getBlockInfo() {
        stockTimerTaskService.getBlockInfo();
    }


}
