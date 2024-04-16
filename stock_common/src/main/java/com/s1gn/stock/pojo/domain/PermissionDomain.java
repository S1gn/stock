package com.s1gn.stock.pojo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName permissionDomain
 * @Description 用户权限实体
 * @Author S1gn
 * @Date 2024/4/16 14:25
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermissionDomain {
    private String title; // 菜单标题
    private String id; // 菜单id
    private String icon; // 菜单图标
    private String path; // 菜单路径
    private String name; // 菜单名称
    private int type; // 菜单类型
    private String pid; // 父菜单id

}
