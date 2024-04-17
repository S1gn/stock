package com.s1gn.stock.vo.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.s1gn.stock.pojo.domain.PermissionDomain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName MenuRespVo
 * @Description 登录返回用户的菜单信息vo
 * @Author S1gn
 * @Date 2024/4/15 22:35
 * @Version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuRespVo {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id; // 菜单id
    private String title; // 菜单标题
    private String icon; // 菜单图标
    private String path; // 菜单路径
    private String name; // 菜单名称
    private List<MenuRespVo> children; // 子菜单

    public static List<MenuRespVo> getMenuList(List<PermissionDomain> permissionLists)
    {
        // 创建一个哈希map，<id, menuRespVo>
        HashMap<String, MenuRespVo> map = new HashMap<>();
        // 创建一个list，存放根菜单
        List<MenuRespVo> rootMenu = new ArrayList<>();
        for(PermissionDomain permissionDomain : permissionLists)
        {
            MenuRespVo menuRespVo = MenuRespVo.builder().
                    id(Long.valueOf(permissionDomain.getId())).
                    title(permissionDomain.getTitle()).
                    icon(permissionDomain.getIcon()).
                    path(permissionDomain.getPath()).
                    name(permissionDomain.getName()).
                    build();
            map.put(permissionDomain.getId(), menuRespVo);
        }
        // 遍历permissionLists，type=3的根据pid找到父菜单，添加到父菜单的children中，type=2的根据pid找到父菜单，添加到父菜单的children中
        for(PermissionDomain permissionDomain : permissionLists)
        {
            if(permissionDomain.getType() == 3) {
                Long pid = Long.valueOf(permissionDomain.getPid());
                if (map.containsKey(pid.toString())) {
                    MenuRespVo parentMenu = map.get(pid.toString());
                    if (parentMenu.getChildren() == null) {
                        parentMenu.setChildren(new ArrayList<>());
                    }
                    parentMenu.getChildren().add(map.get(permissionDomain.getId()));
                }
            }
            else if(permissionDomain.getType() == 2)
            {
                Long pid = Long.valueOf(permissionDomain.getPid());
                if (map.containsKey(pid.toString())) {
                    MenuRespVo parentMenu = map.get(pid.toString());
                    if (parentMenu.getChildren() == null) {
                        parentMenu.setChildren(new ArrayList<>());
                    }
                    parentMenu.getChildren().add(map.get(permissionDomain.getId()));
                }
            }
            else {
                rootMenu.add(map.get(permissionDomain.getId()));
            }
        }
        return rootMenu;
    }


}
