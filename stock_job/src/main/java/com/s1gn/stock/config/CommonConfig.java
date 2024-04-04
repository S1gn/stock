package com.s1gn.stock.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.s1gn.stock.pojo.vo.StockInfoConfig;
import com.s1gn.stock.utils.IdWorker;
import com.s1gn.stock.utils.ParserStockInfoUtil;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @ClassName CommonConfig
 * @Description 定义公共配置bean
 * @Author S1gn
 * @Date 19:27
 * @Version 1.0
 */
@Configuration
@EnableConfigurationProperties({StockInfoConfig.class})
public class CommonConfig {

    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1, 1);
    }

    /**
     * @Auther s1gn
     * @Description 解析股票信息工具类
     * @Date 2024/4/4 16:14
     * @param idWorker
     * @return {@link ParserStockInfoUtil }
     **/
    @Bean
    public ParserStockInfoUtil parserStockInfoUtil(IdWorker idWorker){
        return new ParserStockInfoUtil(idWorker);
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}
