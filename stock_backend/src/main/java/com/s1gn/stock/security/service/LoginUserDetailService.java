package com.s1gn.stock.security.service;

import com.s1gn.stock.face.UserCacheFace;
import com.s1gn.stock.face.impl.UserCacheFaceImpl;
import com.s1gn.stock.mapper.SysRoleMapper;
import com.s1gn.stock.mapper.SysUserMapper;
import com.s1gn.stock.pojo.domain.PermissionDomain;
import com.s1gn.stock.pojo.entity.SysRole;
import com.s1gn.stock.pojo.entity.SysUser;
import com.s1gn.stock.security.detail.LoginUserDetail;
import com.s1gn.stock.vo.resp.MenuRespVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @ClassName LoginUserDetailService
 * @Description
 * @Author S1gn
 * @Date 2024/4/24 13:25
 * @Version 1.0
 */
@Component
public class LoginUserDetailService implements UserDetailsService{
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private UserCacheFaceImpl userCacheFace;
    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
        // 通过用户名查询用户信息
        SysUser user = sysUserMapper.selectByUserName(loginName);
        if(user==null){
            throw new UsernameNotFoundException("用户不存在");
        }
        String userId = user.getId().toString();
        List<String> auths = userCacheFace.getUserAuths(userId);
        List<MenuRespVo> menus = userCacheFace.getUserMenus(userId);
        List<String> perms = userCacheFace.getUserPermissions(userId);

        String[] permissionArray = perms.toArray(new String[perms.size()]);
        // 将用户权限标识转化为权限对象集合
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(permissionArray);
        // 封装用户信息至UserDetails对象中
        LoginUserDetail loginUserDetail = new LoginUserDetail();
        BeanUtils.copyProperties(user, loginUserDetail);
        loginUserDetail.setId(user.getId().toString());
        loginUserDetail.setAuthorities(authorityList);
        loginUserDetail.setMenus(menus);
        loginUserDetail.setPermissions(perms);
        return loginUserDetail;
    }
}
