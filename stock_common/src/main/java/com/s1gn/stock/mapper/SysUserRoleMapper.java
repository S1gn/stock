package com.s1gn.stock.mapper;

import com.s1gn.stock.pojo.entity.SysUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author zzy
* @description 针对表【sys_user_role(用户角色表)】的数据库操作Mapper
* @createDate 2024-03-20 15:13:40
* @Entity com.s1gn.stock.pojo.entity.SysUserRole
*/
public interface SysUserRoleMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

    SysUserRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUserRole record);

    int updateByPrimaryKey(SysUserRole record);

    /**
     * @Auther s1gn
     * @Description 根据用户id删除用户角色
     * @Date 2024/4/16 19:48
     * @param userId
     * @return void
     **/
    void deleteUserRole(@Param("userId") String userId);

    /**
     * @Auther s1gn
     * @Description 批量添加用户角色关联关系
     * @Date 2024/4/16 19:52
     * @param userRoles
     * @return void
     **/
    void addUserRoleList(@Param("userRoles") List<SysUserRole> userRoles);
}
