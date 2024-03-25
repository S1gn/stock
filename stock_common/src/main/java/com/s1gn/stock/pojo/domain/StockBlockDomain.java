package com.s1gn.stock.pojo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName StockBlockDomain
 * @Description
 * @Author S1gn
 * @Date 22:19
 * @Version 1.0
 */
@Data
public class StockBlockDomain {

   private Integer companyNum;
   private Long tradeAmt;
   private String code;
   private BigDecimal avgPrice;
   private String name;
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
   private Date curDate;
   private Long tradeVol;
   private BigDecimal updownRate;
}
