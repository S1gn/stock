package com.s1gn.stock;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName TestRestTemplate
 * @Description
 * @Author S1gn
 *
 * @Date 2024/4/2 16:28
 * @Version 1.0
 */
@SpringBootTest
public class TestRestTemplate {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void test() {
        String url = "http://localhost:8080/stock/stockInfo";
        String result = restTemplate.getForObject(url, String.class);
        System.out.println(result);
    }
}
