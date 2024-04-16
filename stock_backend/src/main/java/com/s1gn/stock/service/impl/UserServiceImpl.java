package com.s1gn.stock.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.date.DateTime;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.s1gn.stock.mapper.*;
import com.s1gn.stock.pojo.entity.*;
import com.s1gn.stock.service.UserService;
import com.s1gn.stock.utils.IdWorker;
import com.s1gn.stock.pojo.domain.PermissionDomain;
import com.s1gn.stock.vo.req.LoginReqVo;
import com.s1gn.stock.vo.req.UpdateUserReqVo;
import com.s1gn.stock.vo.req.UserReqVo;
import com.s1gn.stock.vo.resp.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.s1gn.stock.constant.StockConstant.CHECK_PREFIX;

/**
 * @ClassName UserServiceImpl
 * @Description 定义用户服务实现
 * @Author S1gn
 * @Date 16:18
 * @Version 1.0
 */
@Service("userService")
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;
    @Autowired
    private SysPermissionMapper sysPermissionMapper;
    /**
     * @Auther s1gn
     * @Description 根据用户名查询用户信息
     * @Date 2024/3/20 16:21
     * @Param * @param userName  
     * @Return * @return {@link com.s1gn.stock.pojo.entity.SysUser }
     **/
    @Override
    public SysUser findByUserName(String userName) {
        return sysUserMapper.selectByUserName(userName);
    }
    /**
     * @Auther s1gn
     * @Description 用户登录
     * @Date 2024/3/20 19:46
     * @Param * @param loginReqVo
     * @Return * @return {@link R< LoginRespVo> }
     **/
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public R<LoginRespVo> login(LoginReqVo vo) {
        //1.判断参数是否合法
        if (vo == null || StringUtils.isBlank(vo.getUsername()) || StringUtils.isBlank(vo.getPassword()) || StringUtils.isBlank(vo.getCode())) {
            return R.error(ResponseCode.DATA_ERROR);
        }
        if(StringUtils.isBlank(vo.getCode()) || StringUtils.isBlank(vo.getSessionId())){
            return R.error(ResponseCode.CHECK_CODE_NOT_EMPTY);
        }
        //校验验证码
        String checkCode = redisTemplate.opsForValue().get(CHECK_PREFIX + vo.getSessionId());
        if(StringUtils.isBlank(checkCode)) {
            return R.error(ResponseCode.CHECK_CODE_EXPIRED);
        }
        if (!vo.getCode().equalsIgnoreCase(checkCode)) {
            return R.error(ResponseCode.CHECK_CODE_ERROR);
        }
        //2.根据用户名查询用户信息，找到密码密文
        SysUser user = sysUserMapper.selectByUserName(vo.getUsername());
        if (user == null) {
            return R.error(ResponseCode.ACCOUNT_NOT_EXISTS);
        }
        //3.比较密码是否一致
        if (!passwordEncoder.matches(vo.getPassword(), user.getPassword())) {
            return R.error(ResponseCode.USERNAME_OR_PASSWORD_ERROR);
        }
        //4.查询用户role集合
        List<String> roleidList = sysUserMapper.getRoleIdList(user.getId().toString());
        //5.根据用户roleid查询权限集合
        List<PermissionDomain> permissionList = sysUserMapper.getPermissionList(roleidList);
        List<String> permissions = new ArrayList<>();
        for(PermissionDomain permissionDomain : permissionList){
            if(permissionDomain.getIcon().length() != 0){
                permissions.add(permissionDomain.getIcon());
            }
        }
        //6.根据权限集合组装菜单信息
        List<MenuRespVo> menuList = MenuRespVo.getMenuList(permissionList);
        //7.生成responseVo
        LoginRespVo respVo = new LoginRespVo();
        BeanUtils.copyProperties(user, respVo); //必须保证属性，名称相同
        respVo.setUserName(user.getUsername());
        respVo.setSex(user.getSex());
        respVo.setNickName(user.getNickName());
        respVo.setStatus(user.getStatus());
        respVo.setPermissions(permissions);
        respVo.setMenus(menuList);
        return R.ok(respVo);

    }
    /**
     * @Auther s1gn
     * @Description 生成图片验证码
     * @Date 2024/3/21 15:41
     * @Param
     * @Return * @return {@link R< Map> }
     **/
    @Override
    public R<Map> getCapthcaCode() {
        //1. 生成验证码
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(250, 40, 4, 5);
        // 获取校验码
        String code = captcha.getCode();
        // 将验证码base64编码
        String base64Code = captcha.getImageBase64();
        //2. 生成sessionId
        String sessionId = String.valueOf(idWorker.nextId());
        log.info("当前生成的图片验证码为：{}, 会话id为：{}", code, sessionId);
        //3. 将验证码和sessionId存入redis
        redisTemplate.opsForValue().set(CHECK_PREFIX + sessionId, code, 5, TimeUnit.MINUTES);
        //4. 返回结果
        Map<String, String> data = new HashMap<>();
        data.put("sessionId", sessionId);
        data.put("imageData", base64Code);
        return R.ok(data);
    }

    @Override
    public R<PageResult> getUserByPage(UserReqVo userReqVo) {
        List<SysUser> userList= sysUserMapper.multiConditionQuery(
                userReqVo.getUsername(), userReqVo.getNickName(),
                userReqVo.getStartTime(), userReqVo.getEndTime());
        PageHelper.startPage(userReqVo.getPageNum(), userReqVo.getPageSize());
        PageInfo<SysUser> pageInfo = new PageInfo<>(userList);
        // 根据页数和每页条数求得实际返回的数据
        PageResult<SysUser> pageResult = new PageResult<>(pageInfo);
        return R.ok(pageResult);
    }

    @Override
    public R addUser(SysUser sysUser) {
        sysUser.setId(idWorker.nextId());
        sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
        sysUser.setDeleted(1);
        sysUser.setCreateTime(DateTime.now());
        sysUser.setUpdateTime(DateTime.now());
        sysUser.setCreateWhere(sysUser.getCreateWhere());
        // 查询数据库是否存在相同用户名
        SysUser user = sysUserMapper.selectByUserName(sysUser.getUsername());
        if (user != null) {
            return R.error(ResponseCode.ACCOUNT_EXISTS_ERROR);
        }
        sysUserMapper.insert(sysUser);
        return R.ok();
    }

    @Override
    public R<Map> getUserRoles(String userId) {
        // 查询用户所包含的所有角色id
        HashMap<String, List> data = new HashMap<String, List>();
        List<String> roleidList = sysUserMapper.getRoleIdList(userId);
        data.put("ownRoleIds", roleidList);
        // 根据用户id查询用户所包含的所有角色
        List<SysRole> allRoleList = sysRoleMapper.getAll();
        data.put("allRole", allRoleList);
        return R.ok(data);
    }

    @Override
    public R updateUserRoles(Map<String, Object> map) {
        // 获取用户id
        String userId = (String) map.get("userId");
        // 获取用户角色id集合
        List<String> roleIds = (List<String>) map.get("roleIds");
        // 删除用户所有角色
        sysUserRoleMapper.deleteUserRole(userId);
        // 添加用户角色
        List<SysUserRole> userRoles = new ArrayList<>();
        for (String roleId : roleIds) {
            if(StringUtils.isBlank(roleId)){
                continue;
            }
            SysUserRole userRole = new SysUserRole();
            userRole.setId(idWorker.nextId());
            userRole.setUserId(Long.parseLong(userId));
            userRole.setRoleId(Long.parseLong(roleId));
            userRole.setCreateTime(DateTime.now());
            userRoles.add(userRole);
        }
        sysUserRoleMapper.addUserRoleList(userRoles);
        return R.ok();
    }

    @Override
    public R deleteUserList(List<Long> userIds) {
        sysUserMapper.deleteBatch(userIds);
        return R.ok();
    }

    @Override
    public R<Map> getUserInfo(String userId) {
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(Long.parseLong(userId));
        HashMap<String, Object> data = new HashMap<>();
        data.put("id", sysUser.getId());
        data.put("username", sysUser.getUsername());
        data.put("nickName", sysUser.getNickName());
        data.put("realName", sysUser.getRealName());
        data.put("phone", sysUser.getPhone());
        data.put("email", sysUser.getEmail());
        data.put("sex", sysUser.getSex());
        data.put("status", sysUser.getStatus());
        return R.ok(data);
    }

    @Override
    public R updateUser(UpdateUserReqVo newUser) {
        // 查询用户是否存在
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(Long.parseLong(newUser.getId()));
        if (sysUser == null) {
            return R.error(ResponseCode.ACCOUNT_NOT_EXISTS);
        }
        // 更新用户信息
        sysUser.setUsername(newUser.getUsername());
        sysUser.setNickName(newUser.getNickName());
        sysUser.setRealName(newUser.getRealName());
        sysUser.setPhone(newUser.getPhone());
        sysUser.setEmail(newUser.getEmail());
        sysUser.setSex(newUser.getSex());
        sysUser.setStatus(newUser.getStatus());
        sysUser.setUpdateTime(DateTime.now());
        sysUserMapper.updateByPrimaryKey(sysUser);
        return R.ok();
    }

    @Override
    public R<PageResult> getRolesByPage(Map pageInfo) {
        PageHelper.startPage((Integer) pageInfo.get("pageNum"), (Integer) pageInfo.get("pageSize"));
        List<SysRole> roleList = sysRoleMapper.getAll();
        PageInfo<SysRole> data = new PageInfo<>(roleList);
        PageResult<SysRole> pageResult = new PageResult<>(data);
        return R.ok(pageResult);
    }

    @Override
    public R<List<MenuRespVo>> getAllPermissionTree() {
        // 查询所有权限
        List<PermissionDomain> permissionLists = sysPermissionMapper.getAll();
        // 根据权限集合组装菜单信息
        List<MenuRespVo> menuList = MenuRespVo.getMenuList(permissionLists);
        return R.ok(menuList);
    }

    @Override
    public R addRolePermission(Map<String, Object> rolePermission) {
        // 获取角色名称、描述，创建角色
        SysRole sysRole = new SysRole();
        sysRole.setId(idWorker.nextId());
        sysRole.setName((String) rolePermission.get("name"));
        sysRole.setDescription((String) rolePermission.get("description"));
        sysRole.setCreateTime(DateTime.now());
        sysRole.setUpdateTime(DateTime.now());
        sysRole.setStatus(1);
        sysRole.setDeleted(1);
        sysRoleMapper.insert(sysRole);
        // 获取角色id，权限id集合，创建角色权限列表
        List< SysRolePermission> rolePermissions = new ArrayList<>();
        List<String> permissionIds = (List<String>) rolePermission.get("permissionsIds");
        for(String permissionId : permissionIds){
            SysRolePermission rolePermission1 = new SysRolePermission();
            rolePermission1.setId(idWorker.nextId());
            rolePermission1.setRoleId(sysRole.getId());
            rolePermission1.setPermissionId(Long.parseLong(permissionId));
            rolePermission1.setCreateTime(DateTime.now());
            rolePermissions.add(rolePermission1);
        }
        sysRolePermissionMapper.insertBatch(rolePermissions);
        return R.ok();
    }

}
