package com.s1gn.stock.service;

/**
 * @ClassName StockTimerTaskService
 * @Description 股票采集定时任务
 * @Author S1gn
 * @Date 2024/4/3 14:56
 * @Version 1.0
 */
public interface StockTimerTaskService {
    /**
     * @Auther s1gn
     * @Description 采集国内大盘数据
     * @Date 2024/4/3 14:57
     **/
    public void getInnerMarketInfo();

    /**
     * @Auther s1gn
     * @Description 采集国内各支股票数据
     * @Date 2024/4/4 15:48
     **/
    public void getInnerStockMarketInfo();

    public void getBlockInfo();
}
