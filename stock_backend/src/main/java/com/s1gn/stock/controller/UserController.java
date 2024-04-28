package com.s1gn.stock.controller;

import com.s1gn.stock.pojo.entity.SysPermission;
import com.s1gn.stock.pojo.entity.SysUser;
import com.s1gn.stock.service.UserService;
import com.s1gn.stock.vo.req.LoginReqVo;
import com.s1gn.stock.vo.req.PermissionAddVo;
import com.s1gn.stock.vo.req.UpdateUserReqVo;
import com.s1gn.stock.vo.req.UserReqVo;
import com.s1gn.stock.vo.resp.LoginRespVo;
import com.s1gn.stock.vo.resp.MenuRespVo;
import com.s1gn.stock.vo.resp.PageResult;
import com.s1gn.stock.vo.resp.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.util.List;
import java.util.Map;
/**
 * @ClassName UserController
 * @Description 用户web层接口资源bean
 * @Author S1gn
 * @Date 16:22
 * @Version 1.0
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;
    /**
     * @Auther s1gn
     * @Description 根据用户名称查询用户信息
     * @Date 2024/3/20 16:27
     * @Param * @param name
     * @Return * @return {@link SysUser }
     **/
    @GetMapping("/user/{userName}")
    public SysUser getUserByUserName(@PathVariable("userName") String name) {
        return userService.findByUserName(name);
    }

    /**
     * @Auther s1gn
     * @Description 用户登录
     * @Date 2024/3/20 19:18
     * @Param * @param loginReqVo
     * @Return * @return {@link com.s1gn.stock.vo.resp.LoginRespVo }
     **/
//    @PostMapping("/login")
//    public R<LoginRespVo> login(@RequestBody LoginReqVo loginReqVo) {
//        R<LoginRespVo> r = userService.login(loginReqVo);
//        return r;
//    }
    /**
     * @Auther s1gn
     * @Description 生成图片验证码
     * @Date 2024/3/21 15:37
     * @Param
     * @Return * @return {@link R<Map> }
     **/
    @GetMapping("/captcha")
    public R<Map> getCaptchaCode() {
        return userService.getCapthcaCode();
    }

    /**
     * @Auther s1gn
     * @Description 多条件综合查询用户分页信息，条件包含：分页信息 用户创建日期范围
     * @Date 2024/4/16 15:42
     * @param userReqVo
     * @return {@link null }
     **/
    @PreAuthorize("hasAnyAuthority('sys:user:list')")
    @PostMapping("/users")
    public R<PageResult> getUserByPage(@RequestBody UserReqVo userReqVo) {
        return userService.getUserByPage(userReqVo);
    }
    /**
     * @Auther s1gn
     * @Description 添加用户
     * @Date 2024/4/16 16:37
     * @param sysUser 用户信息
     * @return {@link R }
     **/
    // TODO: 创建人员的信息暂时没有处理
    @PreAuthorize("hasAnyAuthority('sys:user:add')")
    @PostMapping("/user")
    public R addUser(@RequestBody SysUser sysUser) {
        return userService.addUser(sysUser);
    }

    @PreAuthorize("hasAnyAuthority('sys:role:detail')")
    @GetMapping("/user/roles/{userId}")
    public R<Map> getUserRoles(@PathVariable("userId") String userId) {
        return userService.getUserRoles(userId);
    }

    @PutMapping("/user/roles")
    @PreAuthorize("hasAnyAuthority('sys:user:role:update')")
    public R updateUserRoles(@RequestBody Map<String, Object> map) {
        return userService.updateUserRoles(map);
    }

    @PreAuthorize("hasAnyAuthority('sys:user:delete')")
    @DeleteMapping("/user")
    public R deleteUser(@RequestBody List<Long> userIds) {
        return userService.deleteUserList(userIds);
    }

    @PermitAll
    @GetMapping("/user/info/{userId}")
    public R<Map> getUserInfo(@PathVariable("userId") String userId) {
        return userService.getUserInfo(userId);
    }
    @PreAuthorize("hasAnyAuthority('sys:user:update')")
    @PutMapping("/user")
    public R updateUser(@RequestBody UpdateUserReqVo newUser) {
        return userService.updateUser(newUser);
    }

    @PreAuthorize("hasAnyAuthority('sys:role:list')")
    @PostMapping("/roles")
    public R<PageResult> getRolesByPage(@RequestBody Map pageInfo) {
        return userService.getRolesByPage(pageInfo);
    }

    @PermitAll
    @GetMapping("/permissions/tree/all")
    public R<List<MenuRespVo>> getAllPermissionTree() {
        return userService.getAllPermissionTree();
    }

    @PreAuthorize("hasAnyAuthority('sys:role:add')")
    @PostMapping("/role")
    public R addRolePermission(@RequestBody Map<String, Object> rolePermission) {
        return userService.addRolePermission(rolePermission);
    }
    /**
     * @Auther s1gn
     * @Description 获取角色关联的所有权限
     * @Date 2024/4/17 14:00
     * @param roleId
     * @return {@link R< List< String>> }
     **/
    @PreAuthorize("hasAnyAuthority('sys:role:detail')")
    @GetMapping("/role/{roleId}")
    public R<List<String>> getRolePermission(@PathVariable("roleId") String roleId) {
        return userService.getRolePermission(roleId);
    }
    /**
     * @Auther s1gn
     * @Description 更新角色权限
     * @Date 2024/4/17 14:09
     * @param rolePermission
     * @return {@link R }
     **/
    @PreAuthorize("hasAnyAuthority('sys:role:update')")
    @PutMapping("/role")
    public R updateRolePermission(@RequestBody Map<String, Object> rolePermission) {
        return userService.updateRolePermission(rolePermission);
    }

    /**
     * @Auther s1gn
     * @Description 删除角色权限
     * @Date 2024/4/17 14:19
     * @param roleId
     * @return {@link R }
     **/
    @PreAuthorize("hasAnyAuthority('sys:role:delete')")
    @DeleteMapping("/role/{roleId}")
    public R deleteRolePermission(@PathVariable("roleId") String roleId) {
        return userService.deleteRolePermission(roleId);
    }

    /**
     * @Auther s1gn
     * @Description 更新角色状态信息
     * @Date 2024/4/17 14:22
     * @param roleId
     * @param status
     * @return {@link R }
     **/
    @PreAuthorize("hasAnyAuthority('sys:role:update')")
    @PostMapping("/role/{roleId}/{status}")
    public R updateRoleStatus(@PathVariable("roleId") String roleId, @PathVariable("status") Integer status) {
        return userService.updateRoleStatus(roleId, status);
    }
    /**
     * @Auther s1gn
     * @Description 获取所有权限
     * @Date 2024/4/17 14:33
     * @return {@link R< List< SysPermission>> }
     **/
    @PreAuthorize("hasAnyAuthority('sys:permission:list')")
    @GetMapping("/permissions")
    public R<List<SysPermission>> getAllPermissions() {
        return userService.getAllPermissions();
    }
    /**
     * @Auther s1gn
     * @Description 添加权限时回显权限树,仅仅显示目录和菜单
     * @Date 2024/4/17 14:34
     * @return {@link R< List< Map>> }
     **/
    @PermitAll
    @GetMapping("/permissions/tree")
    public R<List<Map>> getPermissionTree() {
        return userService.getPermissionTree();
    }

    @PreAuthorize("hasAnyAuthority('sys:permission:add')")
    @PostMapping("/permission")
    public R addPermission(@RequestBody PermissionAddVo permissionAddVo) {
        return userService.addPermission(permissionAddVo);
    }

    /**
     * @Auther s1gn
     * @Description 更新权限
     * @Date 2024/4/17 14:59
     * @param permissionAddVo
     * @return {@link R }
     **/
    @PreAuthorize("hasAnyAuthority('sys:permission:update')")
    @PutMapping("/permission")
    public R updatePermission(@RequestBody PermissionAddVo permissionAddVo) {
        return userService.updatePermission(permissionAddVo);
    }
    /**
     * @Auther s1gn
     * @Description 删除权限
     * @Date 2024/4/17 15:01
     * @param permissionId
     * @return {@link R }
     **/
    @PreAuthorize("hasAnyAuthority('sys:permission:delete')")
    @DeleteMapping("/permission/{permissionId}")
    public R deletePermission(@PathVariable("permissionId") String permissionId) {
        return userService.deletePermission(permissionId);
    }
}
