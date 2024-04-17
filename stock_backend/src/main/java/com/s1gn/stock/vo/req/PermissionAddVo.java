package com.s1gn.stock.vo.req;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName PermissionAddVo
 * @Description 添加权限vo
 * @Author S1gn
 * @Date 2024/4/17 14:45
 * @Version 1.0
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class PermissionAddVo {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id; //权限id
    private Integer type;//菜单等级 0 顶级目录 1.目录 2 菜单 3 按钮
    private String title;// 更新角色权限
    /**
     * 对应资源路径
     *  1.如果类型是目录，则url为空
     *  2.如果类型是菜单，则url对应路由地址
     *  3.如果类型是按钮，则url对应是访问接口的地址
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long pid;//父级id
    private String url;//只有菜单类型有名称，默认是路由的名称
    private String name;
    private String icon;
    private String perms; //基于springSecrutiry约定的权限过滤便是
    private String method; //请求方式：get put delete post等
    private String code; //vue按钮回显控制辨识
    @JsonSerialize(using = ToStringSerializer.class)
    private Integer orderNum; //排序
}
