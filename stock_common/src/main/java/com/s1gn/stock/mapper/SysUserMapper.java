package com.s1gn.stock.mapper;

import com.s1gn.stock.pojo.entity.SysUser;
import org.apache.ibatis.annotations.Param;

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

}
