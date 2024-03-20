package com.s1gn.stock;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @ClassName TestPasswordEncoder
 * @Description 测试密码加密
 * @Author S1gn
 * @Date 19:30
 * @Version 1.0
 */
@SpringBootTest
public class TestPasswordEncoder {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void test01() {
        String password = "123456";
        String encodePwd = passwordEncoder.encode(password);
        // $2a$10$7ouKUb7yIAKRJcfSogsMlusjB3QNA0caW06ILmAlBVwnWZpfPUypS

    }

    @Test
    public void test02() {
        String password = "123456";
        String enPwd = "$2a$10$7ouKUb7yIAKRJcfSogsMlusjB3QNA0caW06ILmAlBVwnWZpfPUypS";
        boolean matches = passwordEncoder.matches(password, enPwd);
        System.out.println(matches);
    }
}
