package com.s1gn.stock.controller;

import com.s1gn.stock.pojo.entity.SysUser;
import com.s1gn.stock.service.UserService;
import com.s1gn.stock.vo.req.LoginReqVo;
import com.s1gn.stock.vo.resp.LoginRespVo;
import com.s1gn.stock.vo.resp.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
/**
 * @ClassName UserController
 * @Description 用户web层接口资源bean
 * @Author S1gn
 * @Date 16:22
 * @Version 1.0
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;
    /**
     * @Auther s1gn
     * @Description 根据用户名称查询用户信息
     * @Date 2024/3/20 16:27
     * @Param * @param name
     * @Return * @return {@link SysUser }
     **/
    @GetMapping("/user/{userName}")
    public SysUser getUserByUserName(@PathVariable("userName") String name) {
        return userService.findByUserName(name);
    }

    /**
     * @Auther s1gn
     * @Description 用户登录
     * @Date 2024/3/20 19:18
     * @Param * @param loginReqVo
     * @Return * @return {@link com.s1gn.stock.vo.resp.LoginRespVo }
     **/
    @PostMapping("/login")
    public R<LoginRespVo> login(@RequestBody LoginReqVo loginReqVo) {
        R<LoginRespVo> r = userService.login(loginReqVo);
        return r;
    }
    /**
     * @Auther s1gn
     * @Description 生成图片验证码
     * @Date 2024/3/21 15:37
     * @Param
     * @Return * @return {@link R<Map> }
     **/
    @GetMapping("/captcha")
    public R<Map> getCaptchaCode() {
        return userService.getCapthcaCode();
    }
}
