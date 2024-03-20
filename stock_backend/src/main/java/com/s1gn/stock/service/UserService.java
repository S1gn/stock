package com.s1gn.stock.service;

import com.s1gn.stock.pojo.entity.SysUser;
import com.s1gn.stock.vo.req.LoginReqVo;
import com.s1gn.stock.vo.resp.LoginRespVo;
import com.s1gn.stock.vo.resp.R;

/**
 * @ClassName UserService
 * @Description
 * @Author S1gn
 * @Date 16:05
 * @Version 1.0
 */
public interface UserService {

    /**
     * @Auther s1gn
     * @Description  根据用户名查询用户信息
     * @Date 2024/3/20 16:14
     * @Param * @param userName  
     * @Return * @return {@link com.s1gn.stock.pojo.entity.SysUser }
     **/
    SysUser findByUserName(String userName);
    /**
     * @Auther s1gn
     * @Description 用户登录
     * @Date 2024/3/20 19:45
     * @Param * @param loginReqVo
     * @Return * @return {@link R< LoginRespVo> }
     **/
    R<LoginRespVo> login(LoginReqVo loginReqVo);
}
