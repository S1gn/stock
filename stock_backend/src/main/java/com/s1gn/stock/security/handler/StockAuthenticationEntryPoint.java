package com.s1gn.stock.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.s1gn.stock.vo.resp.R;
import com.s1gn.stock.vo.resp.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName StockAuthenticationEntryPoint
 * @Description 未认证的用户访问被拒绝的处理器
 * @Author S1gn
 * @Date 2024/4/24 15:17
 * @Version 1.0
 */
@Slf4j
public class StockAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        log.info("用户访问被拒绝，没有认证");
        R<Object> r = R.error(ResponseCode.NOT_PERMISSION);
        String respStr = new ObjectMapper().writeValueAsString(r);
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.getWriter().write(respStr);
    }
}
