package com.s1gn.stock.mq;

import com.github.benmanes.caffeine.cache.Cache;
import com.s1gn.stock.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @ClassName StockMQMsgListener
 * @Description 股票mq消息监听
 * @Author S1gn
 * @Date 2024/4/5 20:27
 * @Version 1.0
 */
@Component
@Slf4j
public class StockMQMsgListener {
    @Autowired
    private Cache<String, Object> caffeineCache;
    @Autowired
    private StockService stockService;

    @RabbitListener(queues = "innerMarketQueue")
    public void refreshInnerMarket(Date startTime){
        // 计算当前时间和发送时间差值，超过1分钟告警
        long diff = new Date().getTime() - startTime.getTime();
        if (diff > 60000l) {
            log.error("刷新大盘mq消息处理时间过长，发送时间：{}", startTime.toString());
        }
        // 刷新缓存
        // 去除旧数据
        caffeineCache.invalidate("innerMarketKey");
        // 调用服务重新获取数据
        stockService.getInnerMarketInfo();
    }
}
