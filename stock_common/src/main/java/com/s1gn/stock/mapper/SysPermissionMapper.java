package com.s1gn.stock.mapper;

import com.s1gn.stock.pojo.domain.PermissionDomain;
import com.s1gn.stock.pojo.entity.SysPermission;

import java.util.List;

/**
* @author zzy
* @description 针对表【sys_permission(权限表（菜单）)】的数据库操作Mapper
* @createDate 2024-03-20 15:13:40
* @Entity com.s1gn.stock.pojo.entity.SysPermission
*/
public interface SysPermissionMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysPermission record);

    int insertSelective(SysPermission record);

    SysPermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysPermission record);

    int updateByPrimaryKey(SysPermission record);
    /**
     * @Auther s1gn
     * @Description 获取所有权限，用于菜单用
     * @Date 2024/4/16 22:32
     * @return {@link List< PermissionDomain> }
     **/
    List<PermissionDomain> getAll();
    /**
     * @Auther s1gn
     * @Description 获取所有权限，获取permission对象
     * @Date 2024/4/17 14:27
     * @return {@link List< SysPermission> }
     **/
    List<SysPermission> getAllPermissions();
}
