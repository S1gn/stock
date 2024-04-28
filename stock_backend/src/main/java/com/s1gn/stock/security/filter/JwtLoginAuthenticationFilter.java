package com.s1gn.stock.security.filter;

import com.alibaba.excel.util.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.s1gn.stock.constant.StockConstant;
import com.s1gn.stock.security.detail.LoginUserDetail;
import com.s1gn.stock.security.utils.JwtTokenUtil;
import com.s1gn.stock.vo.req.LoginReqVo;
import com.s1gn.stock.vo.resp.LoginRespVo;
import com.s1gn.stock.vo.resp.R;
import com.s1gn.stock.vo.resp.ResponseCode;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName JwtLoginAuthenticationFilter
 * @Description jwt登录认证过滤器
 * @Author S1gn
 * @Date 2024/4/23 16:22
 * @Version 1.0
 */
public class JwtLoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private RedisTemplate<String, String> redisTemplate;
    /**
     * @Auther s1gn
     * @Description 通过构造方法注入登录url
     * @Date 2024/4/23 16:34
     * @param loginUrl
     * @return {@link null }
     **/
    public JwtLoginAuthenticationFilter(String loginUrl) {
        super(loginUrl);
    }
    /**
     * @Auther s1gn
     * @Description 用户认证处理
     * @Date 2024/4/23 16:34
     * @param httpServletRequest
     * @param httpServletResponse
     * @return {@link Authentication }
     **/
    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        //1.首先判断请求是否为post请求，必须是application/json请求
        if(!httpServletRequest.getMethod().equals("POST")
                ||!(httpServletRequest.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)
                || httpServletRequest.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_UTF8_VALUE))){
            throw new AuthenticationServiceException("请求方法不支持或者请求格式不正确");
            }
        //2.获取request中的数据流
        ServletInputStream in = httpServletRequest.getInputStream();
        //将数据流反序列化为对象
        LoginReqVo vo = new ObjectMapper().readValue(in, LoginReqVo.class);
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        //判断参数是否合法
        if (vo==null || StringUtils.isBlank(vo.getUsername())
                || StringUtils.isBlank(vo.getPassword())
                || StringUtils.isBlank(vo.getSessionId()) || StringUtils.isBlank(vo.getCode())) {
            R<Object> resp = R.error(ResponseCode.USERNAME_OR_PASSWORD_ERROR.getMsg());
            httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(resp));
            return null;
        }
        //判断验证码是否正确
        String rCheckCode = redisTemplate.opsForValue().get(StockConstant.CHECK_PREFIX + vo.getSessionId());
        if (rCheckCode==null || ! rCheckCode.equalsIgnoreCase(vo.getCode())) {
            //响应验证码输入错误
            R<Object> resp = R.error(ResponseCode.CHECK_CODE_ERROR.getMsg());
            httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(resp));
            return null;
        }
        String username = vo.getUsername();
        //username = (username != null) ? username : "";
        username = username.trim();
        String password = vo.getPassword();
        //password = (password != null) ? password : "";
        //将用户名和密码封装成token对象
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        // 调用AuthenticationManager的authenticate方法进行认证，返回认证后的Authentication对象
        return this.getAuthenticationManager().authenticate(authRequest);

    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * @Auther s1gn
     * @Description 认证成功后的处理
     * @Date 2024/4/24 13:57
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @return void
     **/
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // 从authResult中获取用户信息
        LoginUserDetail userDetail = (LoginUserDetail) authResult.getPrincipal();
        // 组装返回的用户信息
        String username = userDetail.getUsername();
        List<GrantedAuthority> authorityList = userDetail.getAuthorities();
        String auStrList = authorityList.toString();
        //复制userdetail到login对象
        LoginRespVo resp = new LoginRespVo();
        BeanUtils.copyProperties(userDetail, resp);
        resp.setId(Long.parseLong(userDetail.getId()));
        //生成token
        String token = JwtTokenUtil.createToken(username, auStrList);
        resp.setAccessToken(token);
        //封装响应结果
        R<Object> r = R.ok(ResponseCode.SUCCESS.getMsg(), resp);
        String respStr = new ObjectMapper().writeValueAsString(r);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(respStr);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        R<Object> resp = R.error(ResponseCode.USERNAME_OR_PASSWORD_ERROR.getMsg());
        response.getWriter().write(new ObjectMapper().writeValueAsString(resp));
    }
}
