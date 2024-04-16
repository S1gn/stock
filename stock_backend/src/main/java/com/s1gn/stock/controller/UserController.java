package com.s1gn.stock.controller;

import com.s1gn.stock.pojo.entity.SysUser;
import com.s1gn.stock.service.UserService;
import com.s1gn.stock.vo.req.LoginReqVo;
import com.s1gn.stock.vo.req.UpdateUserReqVo;
import com.s1gn.stock.vo.req.UserReqVo;
import com.s1gn.stock.vo.resp.LoginRespVo;
import com.s1gn.stock.vo.resp.MenuRespVo;
import com.s1gn.stock.vo.resp.PageResult;
import com.s1gn.stock.vo.resp.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/login")
    public R<LoginRespVo> login(@RequestBody LoginReqVo loginReqVo) {
        R<LoginRespVo> r = userService.login(loginReqVo);
        return r;
    }
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
    @PostMapping("/user")
    public R addUser(@RequestBody SysUser sysUser) {
        return userService.addUser(sysUser);
    }

    @GetMapping("/user/roles/{userId}")
    public R<Map> getUserRoles(@PathVariable("userId") String userId) {
        return userService.getUserRoles(userId);
    }

    @PutMapping("/user/roles")
    public R updateUserRoles(@RequestBody Map<String, Object> map) {
        return userService.updateUserRoles(map);
    }

    @DeleteMapping("/user")
    public R deleteUser(@RequestBody List<Long> userIds) {
        return userService.deleteUserList(userIds);
    }

    @GetMapping("/user/info/{userId}")
    public R<Map> getUserInfo(@PathVariable("userId") String userId) {
        return userService.getUserInfo(userId);
    }

    @PutMapping("/user")
    public R updateUser(@RequestBody UpdateUserReqVo newUser) {
        return userService.updateUser(newUser);
    }

    @PostMapping("/roles")
    public R<PageResult> getRolesByPage(@RequestBody Map pageInfo) {
        return userService.getRolesByPage(pageInfo);
    }

    @GetMapping("/permissions/tree/all")
    public R<List<MenuRespVo>> getAllPermissionTree() {
        return userService.getAllPermissionTree();
    }

    @PostMapping("/role")
    public R addRolePermission(@RequestBody Map<String, Object> rolePermission) {
        return userService.addRolePermission(rolePermission);
    }
}
