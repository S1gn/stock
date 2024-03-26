package com.s1gn.stock.pojo.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName StockUpdownDomain
 * @Description 股票涨跌信息
 * @Author S1gn
 * @Date 16:04
 * @Version 1.0
 */
@Data
public class StockUpdownDomain {
    @ExcelProperty(value = {"股票信息","股票代码"}, index = 0)
    private String code;
    @ExcelProperty(value = {"股票信息","股票名称"}, index = 1)
    private String name;
    @ExcelProperty(value = {"股票信息","收盘价"}, index = 2)
    private BigDecimal preClosePrice;
    @ExcelProperty(value = {"股票信息","当前价格"}, index = 3)
    private BigDecimal tradePrice;
    @ExcelProperty(value = {"股票信息","涨跌"}, index = 4)
    private BigDecimal increase;
    @ExcelProperty(value = {"股票信息","涨幅"}, index = 5)
    private BigDecimal upDown;
    @ExcelProperty(value = {"股票信息","振幅"}, index = 6)
    private BigDecimal amplitude;
    @ExcelProperty(value = {"股票信息","成交额"}, index = 7)
    private Long tradeAmt;
    @ExcelProperty(value = {"股票信息","成交量"}, index = 8)
    private BigDecimal tradeVol;
    @ExcelProperty(value = {"股票信息","日期"}, index = 9)
    @DateTimeFormat("yyyy-MM-dd HH:mm")
    /**
     * 日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date curDate;
}
