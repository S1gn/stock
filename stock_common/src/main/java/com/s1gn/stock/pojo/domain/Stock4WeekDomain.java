package com.s1gn.stock.pojo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName Stock4WeekDomain
 * @Description 周K线图数据
 * @Author S1gn
 * @Date 2024/4/12 15:51
 * @Version 1.0
 */
@Data
public class Stock4WeekDomain {
    private BigDecimal avgPrice; // 平均价格
    private BigDecimal minPrice; // 最低价格
    private BigDecimal openPrice; // 开盘价格
    private BigDecimal maxPrice; // 最高价格
    private BigDecimal closePrice; // 收盘价格
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm",timezone = "Asia/Shanghai")
    private Date mxTime;// 最大时间
    private String stockCode; // 股票代码
}
