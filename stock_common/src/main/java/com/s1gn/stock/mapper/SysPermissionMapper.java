package com.s1gn.stock.mapper;

import com.s1gn.stock.pojo.entity.SysPermission;

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

}
