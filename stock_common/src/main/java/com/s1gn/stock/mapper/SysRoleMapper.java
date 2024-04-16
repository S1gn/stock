package com.s1gn.stock.mapper;

import com.s1gn.stock.pojo.entity.SysRole;
import com.s1gn.stock.pojo.entity.SysRolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author zzy
* @description 针对表【sys_role(角色表)】的数据库操作Mapper
* @createDate 2024-03-20 15:13:40
* @Entity com.s1gn.stock.pojo.entity.SysRole
*/
public interface SysRoleMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);
    /**
     * @Auther s1gn
     * @Description 根据roleid集合查询角色列表
     * @Date 2024/4/16 19:14
     * @param roleidList
     * @return {@link List< SysRole> }
     **/
    List<SysRole> getRoleByIdList(@Param("roleidList") List<String> roleidList);
    /**
     * @Auther s1gn
     * @Description 查询所有角色
     * @Date 2024/4/16 19:43
     * @return {@link List< SysRole> }
     **/
    List<SysRole> getAll();

}
