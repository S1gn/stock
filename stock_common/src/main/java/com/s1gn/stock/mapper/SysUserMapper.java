package com.s1gn.stock.mapper;

import com.s1gn.stock.pojo.domain.PermissionDomain;
import com.s1gn.stock.pojo.entity.SysUser;
import com.s1gn.stock.pojo.entity.SysUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
* @author zzy
* @description 针对表【sys_user(用户表)】的数据库操作Mapper
* @createDate 2024-03-20 15:13:40
* @Entity com.s1gn.stock.pojo.entity.SysUser
*/
public interface SysUserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser selectByUserName(@Param("userName") String userName);
    /**
     * @Auther s1gn
     * @Description 根据用户id查询roleid集合
     * @Date 2024/4/15 23:06
     * @param id
     * @return {@link List< String> }
     **/
    List<String> getRoleIdList(@Param("id") String id);

    /**
     * @Auther s1gn
     * @Description 根据roleid集合查询权限列表
     * @Date 2024/4/16 14:30
     * @param roleidList
     * @return {@link List< PermissionDomain> }
     **/
    List<PermissionDomain> getPermissionList(@Param("roleidList") List<String> roleidList);

    /**
     * @Auther s1gn
     * @Description 多条件查询用户列表
     * @Date 2024/4/16 15:59
     * @param username
     * @param nickName
     * @param startTime
     * @param endTime
     * @return {@link List< SysUser> }
     **/
    List<SysUser> multiConditionQuery(@Param("username") String username
            , @Param("nickName") String nickName, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /**
     * @Auther s1gn
     * @Description 批量删除用户
     * @Date 2024/4/16 22:05
     * @param userIds
     * @return void
     **/
    void deleteBatch(@Param("userIds") List<Long> userIds);
}
