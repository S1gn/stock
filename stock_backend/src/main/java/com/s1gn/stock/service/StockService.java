package com.s1gn.stock.service;

import com.s1gn.stock.pojo.domain.InnerMarketDomain;
import com.s1gn.stock.pojo.domain.StockBlockDomain;
import com.s1gn.stock.pojo.domain.StockUpdownDomain;
import com.s1gn.stock.vo.resp.PageResult;
import com.s1gn.stock.vo.resp.R;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface StockService {
    R<List<InnerMarketDomain>> getInnerMarketInfo();

    R<List<StockBlockDomain>> sectorAllLimit();

    R<PageResult<StockUpdownDomain>> getSockInfoByPage(Integer page, Integer pageSize);

    R<List<StockUpdownDomain>> getStockIncreaseInfo();

    R<Map<String, List>> getStockUpDownCount();
}
