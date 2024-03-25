package com.s1gn.stock.config;

import com.s1gn.stock.pojo.vo.StockInfoConfig;
import com.s1gn.stock.utils.IdWorker;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    /**
     * @Auther s1gn
     * @Description 定义密码加密，匹配器bean
     * @Date 2024/3/20 19:28
     * @Param 
     * @Return * @return {@link PasswordEncoder }
     **/
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * @Auther s1gn
     * @Description 雪花算法id生成器
     * @Date 2024/3/21 15:27
     * @Param
     * @Return * @return {@link IdWorker }
     **/
    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1, 1);
    }
}
