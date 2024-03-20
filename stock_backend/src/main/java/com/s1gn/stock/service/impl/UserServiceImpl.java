package com.s1gn.stock.service.impl;

import com.s1gn.stock.mapper.SysUserMapper;
import com.s1gn.stock.pojo.entity.SysUser;
import com.s1gn.stock.service.UserService;
import com.s1gn.stock.vo.req.LoginReqVo;
import com.s1gn.stock.vo.resp.LoginRespVo;
import com.s1gn.stock.vo.resp.R;
import com.s1gn.stock.vo.resp.ResponseCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserServiceImpl
 * @Description 定义用户服务实现
 * @Author S1gn
 * @Date 16:18
 * @Version 1.0
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    /**
     * @Auther s1gn
     * @Description 根据用户名查询用户信息
     * @Date 2024/3/20 16:21
     * @Param * @param userName  
     * @Return * @return {@link com.s1gn.stock.pojo.entity.SysUser }
     **/
    @Override
    public SysUser findByUserName(String userName) {
        return sysUserMapper.selectByUserName(userName);
    }
    /**
     * @Auther s1gn
     * @Description 用户登录
     * @Date 2024/3/20 19:46
     * @Param * @param loginReqVo
     * @Return * @return {@link R< LoginRespVo> }
     **/
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public R<LoginRespVo> login(LoginReqVo vo) {
        //1.判断参数是否合法
        if (vo == null || StringUtils.isBlank(vo.getUserName()) || StringUtils.isBlank(vo.getPassword()) || StringUtils.isBlank(vo.getCheckCode())) {
            return R.error(ResponseCode.DATA_ERROR);
        }
        //2.根据用户名查询用户信息，找到密码密文
        SysUser user = sysUserMapper.selectByUserName(vo.getUserName());
        if (user == null) {
            return R.error(ResponseCode.ACCOUNT_NOT_EXISTS);
        }
        //3.比较密码是否一致
        if (!passwordEncoder.matches(vo.getPassword(), user.getPassword())) {
            return R.error(ResponseCode.USERNAME_OR_PASSWORD_ERROR);
        }
        //4.生成responseVo
        LoginRespVo respVo = new LoginRespVo();
        BeanUtils.copyProperties(user, respVo); //必须保证属性，名称相同
        return R.ok(respVo);

    }
}
