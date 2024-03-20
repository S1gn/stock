package com.s1gn.stock;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("com.s1gn.stock.mapper") //扫描持久层mapper接口，生成代理对象，维护到IOC容器中，可以直接注入使用
@SpringBootApplication
public class BackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }
}
