package com.s1gn.stock;

import com.s1gn.stock.service.StockTimerTaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @ClassName TestRestTemplate
 * @Description
 * @Author S1gn
 * @Date 2024/4/3 15:30
 * @Version 1.0
 */

@SpringBootTest
public class TestRestTemplate {

    @Autowired
    private StockTimerTaskService stockTimerTaskService;

    @Test
    public void testGetInnerMarketInfo() {
        stockTimerTaskService.getInnerMarketInfo();
    }

    @Test
    public void testGetInnerStockMarketInfo() {
        stockTimerTaskService.getInnerStockMarketInfo();
    }

    @Test
    public void testGetBlockInfo() {
        stockTimerTaskService.getBlockInfo();
    }

    @Test
    public void testGetOuterMarketInfo() {
        stockTimerTaskService.getOuterMarketInfo();
    }
}
