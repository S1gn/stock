package com.s1gn.stock;

import com.s1gn.stock.mapper.SysUserMapper;
import com.s1gn.stock.pojo.entity.SysUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @ClassName TestSharding
 * @Description
 * @Author S1gn
 * @Date 2024/4/22 21:37
 * @Version 1.0
 */
@SpringBootTest
public class TestSharding {
    @Autowired
    private SysUserMapper sysUserMapper;
    /**
     * 测试默认数据源
     */
    @Test
    public void testDefaultDs(){
        SysUser user = sysUserMapper.selectByPrimaryKey(1237365636208922624l);
        System.out.println(user);
    }
}