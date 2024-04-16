package com.s1gn.stock.pojo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName Stock4SecondDomain
 * @Description 股票最新分时数据
 * @Author S1gn
 * @Date 2024/4/12 16:41
 * @Version 1.0
 */
@Data
public class Stock4SecondDomain {
    private Long tradeAmt; // 交易量
    private BigDecimal preClosePrice; // 前收盘价
    private BigDecimal lowPrice; // 最低价
    private BigDecimal highPrice; // 最高价
    private BigDecimal openPrice; // 开盘价
    private BigDecimal tradeVol; // 当前交易总金额
    private BigDecimal tradePrice; // 当前价格
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date curDate;// 当前时间
}
