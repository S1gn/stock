package com.s1gn.stock.job;

import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

/**
 * @ClassName StockJob
 * @Description 定义xxl-job任务执行器bean
 * @Author S1gn
 * @Date 2024/4/7 23:06
 * @Version 1.0
 */
@Component
public class StockJob {
    @XxlJob("stockJobHandler")
    public void stockJobHandler() {
        System.out.println("stockJobHandler");
    }
}
