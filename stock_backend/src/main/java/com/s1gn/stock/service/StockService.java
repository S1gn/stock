package com.s1gn.stock.service;

import com.s1gn.stock.pojo.domain.InnerMarketDomain;
import com.s1gn.stock.pojo.domain.StockBlockDomain;
import com.s1gn.stock.vo.resp.R;
import org.springframework.stereotype.Service;

import java.util.List;


public interface StockService {
    R<List<InnerMarketDomain>> getInnerMarketInfo();

    R<List<StockBlockDomain>> sectorAllLimit();
}
