package com.s1gn.stock.vo.req;

import lombok.Data;

/**
 * @ClassName LoginReqVo
 * @Description
 * @Author S1gn
 * @Date 19:16
 * @Version 1.0
 */
@Data
public class LoginReqVo {
    private String username;
    private String password;
    private String code;
    private String sessionId;
}
