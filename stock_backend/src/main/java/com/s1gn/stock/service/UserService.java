package com.s1gn.stock.service;

import com.s1gn.stock.pojo.entity.SysUser;
import com.s1gn.stock.vo.req.LoginReqVo;
import com.s1gn.stock.vo.req.UpdateUserReqVo;
import com.s1gn.stock.vo.req.UserReqVo;
import com.s1gn.stock.vo.resp.LoginRespVo;
import com.s1gn.stock.vo.resp.MenuRespVo;
import com.s1gn.stock.vo.resp.PageResult;
import com.s1gn.stock.vo.resp.R;

import java.util.List;
import java.util.Map;

/**
 * @ClassName UserService
 * @Description
 * @Author S1gn
 * @Date 16:05
 * @Version 1.0
 */
public interface UserService {

    /**
     * @Auther s1gn
     * @Description  根据用户名查询用户信息
     * @Date 2024/3/20 16:14
     * @Param * @param userName  
     * @Return * @return {@link com.s1gn.stock.pojo.entity.SysUser }
     **/
    SysUser findByUserName(String userName);
    /**
     * @Auther s1gn
     * @Description 用户登录
     * @Date 2024/3/20 19:45
     * @Param * @param loginReqVo
     * @Return * @return {@link R< LoginRespVo> }
     **/
    R<LoginRespVo> login(LoginReqVo loginReqVo);

    R<Map> getCapthcaCode();

    /**
     * @Auther s1gn
     * @Description 分页查询用户信息
     * @Date 2024/4/16 15:48
     * @param userReqVo
     * @return {@link R< PageResult> }
     **/
    R<PageResult> getUserByPage(UserReqVo userReqVo);

    /**
     * @Auther s1gn
     * @Description 创建用户
     * @Date 2024/4/16 16:38
     * @param sysUser
     * @return {@link R }
     **/
    R addUser(SysUser sysUser);

    /**
     * @Auther s1gn
     * @Description 查找用户所包含的所有角色
     * @Date 2024/4/16 16:57
     * @param userId
     * @return {@link R< Map> }
     **/
    R<Map> getUserRoles(String userId);
    /**
     * @Auther s1gn
     * @Description 更新用户角色
     * @Date 2024/4/16 19:45
     * @param map
     * @return {@link R }
     **/
    R updateUserRoles(Map<String, Object> map);
    /**
     * @Auther s1gn
     * @Description 批量删除用户
     * @Date 2024/4/16 22:04
     * @param userIds
     * @return {@link R }
     **/
    R deleteUserList(List<Long> userIds);
    /**
     * @Auther s1gn
     * @Description 获取用户信息
     * @Date 2024/4/16 22:08
     * @param userId
     * @return {@link R< Map> }
     **/
    R<Map> getUserInfo(String userId);
    /**
     * @Auther s1gn
     * @Description 更新用户信息
     * @Date 2024/4/16 22:18
     * @param newUser
     * @return {@link R }
     **/
    R updateUser(UpdateUserReqVo newUser);
    /**
     * @Auther s1gn
     * @Description 分页查询角色信息
     * @Date 2024/4/16 22:25
     * @param pageInfo
     * @return {@link R< PageResult> }
     **/
    R<PageResult> getRolesByPage(Map pageInfo);

    /**
     * @Auther s1gn
     * @Description 获取所有权限树
     * @Date 2024/4/16 22:29
     * @return {@link R< List< MenuRespVo>> }
     **/
    R<List<MenuRespVo>> getAllPermissionTree();
    /**
     * @Auther s1gn
     * @Description 添加角色和角色关联权限
     * @Date 2024/4/16 22:41
     * @param rolePermission
     * @return {@link R }
     **/
    R addRolePermission(Map<String, Object> rolePermission);
}
