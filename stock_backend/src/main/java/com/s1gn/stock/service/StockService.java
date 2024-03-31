package com.s1gn.stock.service;

import com.s1gn.stock.pojo.domain.*;
import com.s1gn.stock.vo.resp.PageResult;
import com.s1gn.stock.vo.resp.R;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


public interface StockService {
    R<List<InnerMarketDomain>> getInnerMarketInfo();

    R<List<StockBlockDomain>> sectorAllLimit();

    R<PageResult<StockUpdownDomain>> getSockInfoByPage(Integer page, Integer pageSize);

    R<List<StockUpdownDomain>> getStockIncreaseInfo();

    R<Map<String, List>> getStockUpDownCount();

    void exportStockUpDownInfo(Integer page, Integer pageSize, HttpServletResponse response);

    R<Map<String, List>> getCompareStockTradeAmt();

    R<Map> getIncreaseRangeInfo();

    R<List<Stock4MinuteDomain>> getStockScreenTimeSharing(String stockCode);

    R<List<Stock4DayDomain>> getStockScreenDKline(String stockCode);
}
