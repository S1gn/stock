package com.s1gn.stock.face;

import com.s1gn.stock.vo.resp.MenuRespVo;

import java.util.List;

public interface UserCacheFace {
    List<String> getUserAuths(String userId);

    List<MenuRespVo> getUserMenus(String userId);

    List<String> getUserPermissions(String userId);
}
