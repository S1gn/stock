package com.s1gn.stock.security.config;

import com.s1gn.stock.security.filter.JwtAuthorizationFilter;
import com.s1gn.stock.security.filter.JwtLoginAuthenticationFilter;
import com.s1gn.stock.security.handler.StockAccessDenyHandler;
import com.s1gn.stock.security.handler.StockAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @ClassName SecurityConfig
 * @Description 安全配置类
 * @Author S1gn
 * @Date 2024/4/24 14:01
 * @Version 1.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 定义公共的无需被拦截的资源
     * @return
     */
    private String[] getPubPath(){
        //公共访问资源
        String[] urls = {
                "/**/*.css","/**/*.js","/favicon.ico","/doc.html",
                "/druid/**","/webjars/**","/v2/api-docs","/api/captcha",
                "/swagger/**","/swagger-resources/**","/swagger-ui.html"
        };
        return urls;
    }

    /**
     * 配置过滤规则
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //设置登录规则
        http.formLogin().permitAll();
        //登出功能
        http.logout().logoutUrl("/api/logout").invalidateHttpSession(true);
        //允许跨域共享
        http.csrf().disable().cors();
        //开启允许iframe 嵌套。security默认禁用firam跨域与缓存
        http.headers().frameOptions().disable().cacheControl().disable();
        //session禁用
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //授权策略
        http.authorizeRequests()//设置需要认证才能访问的请求
                .antMatchers(getPubPath()).permitAll()
                .anyRequest()
                .authenticated();//其他所有请求都需要认证
        //认证和授权设置(登录认证和授权检查)
        http
                //自定义的登录认证过滤器在内置认证过滤之前，这样认证生成token对象后，就不会走默认认证过滤器
                .addFilterBefore(jwtLoginAuthenticationFilter(),UsernamePasswordAuthenticationFilter.class)
                //授权过滤要在登录认证过滤之前，保证认证通过的资源无需经过登录认证过滤器
                .addFilterBefore(jwtAuthorizationFilter(),JwtLoginAuthenticationFilter.class);
        //注册匿名访问拒绝处理器和认证失败处理器
        http.exceptionHandling()
                //未经过认证的匿名用户处理
                .authenticationEntryPoint(new StockAuthenticationEntryPoint())
                //权限不足的处理
                .accessDeniedHandler(new StockAccessDenyHandler());
    }

    /**
     * 自定义认证过滤器bean
     * @return
     * @throws Exception
     */
    @Bean
    public JwtLoginAuthenticationFilter jwtLoginAuthenticationFilter() throws Exception {
        JwtLoginAuthenticationFilter filter = new JwtLoginAuthenticationFilter("/api/login");
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setRedisTemplate(redisTemplate);
        return filter;
    }

    /**
     * 定义密码加密器，实现对明文密码的加密和匹配操作
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * @Auther s1gn
     * @Description 自定义授权过滤器
     * @Date 2024/4/24 15:12
     * @return {@link JwtAuthorizationFilter }
     **/
    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter(){
        return new JwtAuthorizationFilter();
    }
}
