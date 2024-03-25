package com.s1gn.stock.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.s1gn.stock.mapper.SysUserMapper;
import com.s1gn.stock.pojo.entity.SysUser;
import com.s1gn.stock.service.UserService;
import com.s1gn.stock.utils.IdWorker;
import com.s1gn.stock.vo.req.LoginReqVo;
import com.s1gn.stock.vo.resp.LoginRespVo;
import com.s1gn.stock.vo.resp.R;
import com.s1gn.stock.vo.resp.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.s1gn.stock.constant.StockConstant.CHECK_PREFIX;

/**
 * @ClassName UserServiceImpl
 * @Description 定义用户服务实现
 * @Author S1gn
 * @Date 16:18
 * @Version 1.0
 */
@Service("userService")
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
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
        if (vo == null || StringUtils.isBlank(vo.getUsername()) || StringUtils.isBlank(vo.getPassword()) || StringUtils.isBlank(vo.getCode())) {
            return R.error(ResponseCode.DATA_ERROR);
        }
        if(StringUtils.isBlank(vo.getCode()) || StringUtils.isBlank(vo.getSessionId())){
            return R.error(ResponseCode.CHECK_CODE_NOT_EMPTY);
        }
        //校验验证码
        String checkCode = redisTemplate.opsForValue().get(CHECK_PREFIX + vo.getSessionId());
        if(StringUtils.isBlank(checkCode)) {
            return R.error(ResponseCode.CHECK_CODE_EXPIRED);
        }
        if (!vo.getCode().equalsIgnoreCase(checkCode)) {
            return R.error(ResponseCode.CHECK_CODE_ERROR);
        }
        //2.根据用户名查询用户信息，找到密码密文
        SysUser user = sysUserMapper.selectByUserName(vo.getUsername());
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
    /**
     * @Auther s1gn
     * @Description 生成图片验证码
     * @Date 2024/3/21 15:41
     * @Param
     * @Return * @return {@link R< Map> }
     **/
    @Override
    public R<Map> getCapthcaCode() {
        //1. 生成验证码
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(250, 40, 4, 5);
        // 获取校验码
        String code = captcha.getCode();
        // 将验证码base64编码
        String base64Code = captcha.getImageBase64();
        //2. 生成sessionId
        String sessionId = String.valueOf(idWorker.nextId());
        log.info("当前生成的图片验证码为：{}, 会话id为：{}", code, sessionId);
        //3. 将验证码和sessionId存入redis
        redisTemplate.opsForValue().set(CHECK_PREFIX + sessionId, code, 5, TimeUnit.MINUTES);
        //4. 返回结果
        Map<String, String> data = new HashMap<>();
        data.put("sessionId", sessionId);
        data.put("imageData", base64Code);
        return R.ok(data);
    }
}
