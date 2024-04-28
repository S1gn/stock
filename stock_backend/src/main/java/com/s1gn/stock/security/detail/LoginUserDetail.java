package com.s1gn.stock.security.detail;

import com.s1gn.stock.vo.resp.MenuRespVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @ClassName LoginUserDetail
 * @Description 自定义用户认证详情
 * @Author S1gn
 * @Date 2024/4/24 13:21
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginUserDetail implements UserDetails {

    private String username;
    private String password;
    private List<GrantedAuthority> authorities;
    private boolean isAccountNonExpired=true;
    private boolean isAccountNonLocked=true;
    private boolean isCredentialsNonExpired=true;
    private boolean isEnabled=true;
    private String id;
    private String phone;
    private String nickName;
    private String realName;
    private String email;
    private Integer status;
    private Integer sex;
    private List<MenuRespVo> menus; // 菜单
    private List<String> permissions; // 权限
}
