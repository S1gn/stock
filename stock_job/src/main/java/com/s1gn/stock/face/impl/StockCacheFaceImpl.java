package com.s1gn.stock.face.impl;

import com.s1gn.stock.face.StockCacheFace;
import com.s1gn.stock.mapper.StockBusinessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName StockCacheFaceImpl
 * @Description 缓存接口实现
 * @Author S1gn
 * @Date 2024/4/26 15:29
 * @Version 1.0
 */
@Component("stockCacheFace")

public class StockCacheFaceImpl implements StockCacheFace {
    @Autowired
    private StockBusinessMapper stockBusinessMapper;

    /**
     * @Auther s1gn
     * @Description 获取所有股票代码
     * @Date 2024/4/26 15:30
     * @return {@link List< String> }
     **/
    @Cacheable(cacheNames = "stock", key = "#root.method.getName()")
    @Override
    public List<String> getAllStockCodeWithPrefix() {
        List<String> allStockCodes = stockBusinessMapper.getAllStockCodes();
        allStockCodes = allStockCodes.stream().map(code -> code.startsWith("6") ? "sh" + code : "sz" + code).collect(Collectors.toList());
        return allStockCodes;
    }
}
