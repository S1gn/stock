package com.s1gn.stock.pojo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName InnerMarketDomain
 * @Description 国内大盘数据对象
 * @Author S1gn
 * @Date 19:53
 * @Version 1.0
 */
@Data
public class InnerMarketDomain {
    private String code; //股票代码
    private String name; //股票名称
    private BigDecimal openPoint; //开盘点数
    private BigDecimal curPoint; //当前点数
    private BigDecimal preClosePoint; //昨日收盘点数
    private Long tradeAmt; //成交金额
    private BigDecimal tradeVol; //成交量
    private BigDecimal upDown; //涨跌额
    private BigDecimal rose; //涨跌幅
    private BigDecimal amplitude; //振幅
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date curTime; //当前时间
}
