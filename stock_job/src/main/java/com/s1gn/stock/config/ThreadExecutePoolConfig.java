package com.s1gn.stock.config;

import com.s1gn.stock.pojo.vo.TaskThreadPoolInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName ThreadExecutePoolConfig
 * @Description 线程池配置类
 * @Author S1gn
 * @Date 2024/4/9 22:21
 * @Version 1.0
 */
@Configuration
public class ThreadExecutePoolConfig {
    @Autowired
    private TaskThreadPoolInfo info;

    @Bean(name = "threadPoolTaskExecutor", destroyMethod = "shutdown")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数
        executor.setCorePoolSize(info.getCorePoolSize());
        // 最大线程数
        executor.setMaxPoolSize(info.getMaxPoolSize());
        // 队列容量
        executor.setQueueCapacity(info.getQueueCapacity());
         // 线程空闲后的最大存活时间
        executor.setKeepAliveSeconds(info.getKeepAliveSeconds());
        // 线程名称前缀
        executor.setThreadNamePrefix("StockExecutor-");
        // 拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        //初始化
        executor.initialize();
        return executor;
    }
}
