package com.s1gn.stock.vo.resp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Long id;
    private String phone;
    private String userName;
    private String nickName;
}
