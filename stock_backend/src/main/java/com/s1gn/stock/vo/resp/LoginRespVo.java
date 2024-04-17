package com.s1gn.stock.vo.resp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName LoginRespVo
 * @Description
 * @Author S1gn
 * @Date 19:17
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRespVo {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id; // 用户id
    private String phone; // 手机号
    private String userName; // 用户名
    private String nickName; // 昵称
    private String realName; // 真实姓名
    private String email; // 邮箱
    private Integer sex; // 性别
    private Integer status; // 状态
    private List<MenuRespVo> menus; // 菜单
    private List<String> permissions; // 权限
    private String accessToken; // 访问令牌
}
