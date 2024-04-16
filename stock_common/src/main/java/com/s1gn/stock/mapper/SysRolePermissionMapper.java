package com.s1gn.stock.mapper;

import com.s1gn.stock.pojo.entity.SysRolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author zzy
* @description 针对表【sys_role_permission(角色权限表)】的数据库操作Mapper
* @createDate 2024-03-20 15:13:40
* @Entity com.s1gn.stock.pojo.entity.SysRolePermission
*/
public interface SysRolePermissionMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysRolePermission record);

    int insertSelective(SysRolePermission record);

    SysRolePermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRolePermission record);

    int updateByPrimaryKey(SysRolePermission record);
    /**
     * @Auther s1gn
     * @Description 批量插入角色权限关联表SY
     * @Date 2024/4/16 23:18
     * @param rolePermissions
     * @return void
     **/
    void insertBatch(@Param("rolePermissions") List<SysRolePermission> rolePermissions);
}
