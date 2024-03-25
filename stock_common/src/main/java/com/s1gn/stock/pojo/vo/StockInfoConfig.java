package com.s1gn.stock.pojo.vo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName StockInfoConfig
 * @Description 定义股票相关值的对象
 * @Author S1gn
 * @Date 21:16
 * @Version 1.0
 */
@Data
@ConfigurationProperties(prefix = "stock") //需要enableConfigurationProperties注解开启
public class StockInfoConfig {
    private List<String> inner; //国内大盘数据

    private List<String> outer; //国外大盘数据
}
