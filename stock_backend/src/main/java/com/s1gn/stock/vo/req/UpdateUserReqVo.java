package com.s1gn.stock.vo.req;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName UpdateUserReqVo
 * @Description 更新用户请求vo
 * @Author S1gn
 * @Date 2024/4/16 22:13
 * @Version 1.0
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class UpdateUserReqVo {
    private String id;
    private String username;
    private String phone;
    private String email;
    private String nickName;
    private String realName;
    private Integer sex;
    private Integer createWhere;
    private Integer status;
}
