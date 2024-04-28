package com.s1gn.stock.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.s1gn.stock.constant.StockConstant;
import com.s1gn.stock.security.utils.JwtTokenUtil;
import com.s1gn.stock.vo.resp.R;
import com.s1gn.stock.vo.resp.ResponseCode;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName JwtAuthorizationFilter
 * @Description 访问授权过滤器
 * @Author S1gn
 * @Date 2024/4/24 14:46
 * @Version 1.0
 */
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    /**
     * @Auther s1gn
     * @Description 访问过滤的方法
     * @Date 2024/4/24 15:03
     * @param httpServletRequest
     * @param httpServletResponse
     * @param filterChain
     * @return
     **/
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //从request下获取token，约定key:Authorization
        String token = httpServletRequest.getHeader(StockConstant.TOKEN_HEADER);
        //判断token是否为空
        if (token == null) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        //token不为空，解析token
        Claims claims = JwtTokenUtil.checkJWT(token);
        //判断claims是否为空
        if (claims == null) {
            R<Object> r = R.error(ResponseCode.INVALID_TOKEN);
            String respStr = new ObjectMapper().writeValueAsString(r);
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.getWriter().write(respStr);
            return;
        }
        // 获取用户名和权限
        String username = JwtTokenUtil.getUsername(token);
        String perms = JwtTokenUtil.getUserRole(token);
        String strip = StringUtils.strip(perms, "[]");
        List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(strip);
        // 将用户信息存入security上下文
        UsernamePasswordAuthenticationToken token1 = new UsernamePasswordAuthenticationToken(username, null, authorityList);
        // 将token对象存入安全上限文，这样，线程无论走到哪里，都可以获取token对象，验证当前用户访问对应资源是否被授权
        SecurityContextHolder.getContext().setAuthentication(token1);
        // 放行
        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}
