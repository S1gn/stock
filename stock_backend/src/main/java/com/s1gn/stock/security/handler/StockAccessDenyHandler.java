package com.s1gn.stock.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.s1gn.stock.vo.resp.R;
import com.s1gn.stock.vo.resp.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName StockAccessDenyHandler
 * @Description 定义没有权限，访问拒绝的处理器
 * 用户认证成功，但是没有访问的权限，则会除非拒绝处理器
 * 如果是匿名用户访问被拒绝则使用匿名拒绝的处理器
 * @Author S1gn
 * @Date 2024/4/24 15:14
 * @Version 1.0
 */
@Slf4j
public class StockAccessDenyHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        log.info("用户访问被拒绝，没有访问权限");
        R<Object> r = R.error(ResponseCode.NOT_PERMISSION);
        String respStr = new ObjectMapper().writeValueAsString(r);
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.getWriter().write(respStr);
    }
}
