package com.s1gn.stock.config;

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
}
