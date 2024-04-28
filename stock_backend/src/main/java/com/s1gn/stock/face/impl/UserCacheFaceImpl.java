package com.s1gn.stock.face.impl;

import com.s1gn.stock.face.UserCacheFace;
import com.s1gn.stock.mapper.SysRoleMapper;
import com.s1gn.stock.mapper.SysUserMapper;
import com.s1gn.stock.pojo.domain.PermissionDomain;
import com.s1gn.stock.pojo.entity.SysRole;
import com.s1gn.stock.vo.resp.MenuRespVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName UserCacheFaceImpl
 * @Description 用户缓存接口实现
 * @Author S1gn
 * @Date 2024/4/28 15:11
 * @Version 1.0
 */
@Component("userCacheFace")
public class UserCacheFaceImpl implements UserCacheFace {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    /**
     * @Auther s1gn
     * @Description 根据用户id获取用户权限
     * @Date 2024/4/28 15:12
     * @param userId
     * @return {@link List< String> }
     **/
    @Override
    @Cacheable(cacheNames = "auths", key = "#userId")
    public List<String> getUserAuths(String userId) {
        List<String> roleidList = sysUserMapper.getRoleIdList(userId);
        //根据用户roleid查询权限集合
        List<PermissionDomain> permissionList = sysUserMapper.getPermissionList(roleidList);
        List<String> permissions = new ArrayList<>();
        for(PermissionDomain permissionDomain : permissionList){
            if(permissionDomain.getPerms().length() != 0){
                permissions.add(permissionDomain.getPerms());
            }
        }
        // 组装角色列表
        List<SysRole> roles = sysRoleMapper.getRoleByIdList(roleidList);
        // 将权限标识和角色标识维护到权限集合中
        List<String> perms = new ArrayList<>();
        permissions.forEach(permission -> {
            if(StringUtils.isNotBlank(permission)){
                perms.add(permission);
            }
        });
        roles.forEach(role -> {
            perms.add("ROLE_" + role.getName());
        });

        return perms;
    }
    /**
     * @Auther s1gn
     * @Description 根据用户id获取用户菜单
     * @Date 2024/4/28 15:12
     * @param userId
     * @return {@link List< MenuRespVo> }
     **/
    @Override
    @Cacheable(cacheNames = "menus", key = "#userId")
    public List<MenuRespVo> getUserMenus(String userId) {
        List<String> roleidList = sysUserMapper.getRoleIdList(userId);
        //根据用户roleid查询权限集合
        List<PermissionDomain> permissionList = sysUserMapper.getPermissionList(roleidList);
        // 根据权限集合组装菜单信息，删除权限按钮即code不为空的，目测type = 3
        List<MenuRespVo> menuList = MenuRespVo.getMenuList(permissionList);
        for(MenuRespVo menuRespVo: menuList)
        {
            if(menuRespVo.getChildren() != null)
            {
                for(MenuRespVo menuRespVo1: menuRespVo.getChildren())
                {
                    if(menuRespVo1.getChildren() != null)
                    {
                        menuRespVo1.setChildren(null);
                    }
                }
            }
        }
        return menuList;
    }

    /**
     * @Auther s1gn
     * @Description 根据用户id获取用户权限
     * @Date 2024/4/28 15:26
     * @param userId
     * @return {@link List< String> }
     **/

    @Override
    @Cacheable(cacheNames = "perms", key = "#userId")
    public List<String> getUserPermissions(String userId) {
        List<String> roleidList = sysUserMapper.getRoleIdList(userId);
        //根据用户roleid查询权限集合
        List<PermissionDomain> permissionList = sysUserMapper.getPermissionList(roleidList);
        List<String> permissions = new ArrayList<>();
        for(PermissionDomain permissionDomain : permissionList){
            if(permissionDomain.getPerms().length() != 0){
                permissions.add(permissionDomain.getPerms());
            }
        }
        return permissions;
    }
}
