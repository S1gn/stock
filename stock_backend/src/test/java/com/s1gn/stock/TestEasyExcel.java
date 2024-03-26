package com.s1gn.stock;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.s1gn.stock.pojo.User;
import org.apache.commons.collections4.iterators.ArrayListIterator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName TestEasyExcel
 * @Description
 * @Author S1gn
 * @Date 21:41
 * @Version 1.0
 */
@SpringBootTest
public class TestEasyExcel {
    public List<User> init(){
        //组装数据
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setAddress("上海"+i);
            user.setUserName("张三"+i);
            user.setBirthday(new Date());
            user.setAge(10+i);
            users.add(user);
        }
        return users;
    }
    @Test
    public void test(){
        List<User> users = init();
        EasyExcel.write("D:\\test.xlsx",User.class).sheet("用户信息").doWrite(users);
    }

    @Test
    public void testRead(){
        ArrayList<User> users = new ArrayList<>();
        EasyExcel.read("D:\\test.xlsx", User.class, new AnalysisEventListener<User>() {
            @Override
            public void invoke(User o, AnalysisContext analysisContext) {
                System.out.println(o);
                users.add(o);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                System.out.println("读取完成");
            }
        }).sheet().doRead();
        System.out.println(users);
    }
}
