package com.s1gn.stock.vo.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName UserReqVo
 * @Description 用户管理界面的查询请求vo
 * @Author S1gn
 * @Date 2024/4/16 15:43
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserReqVo {
    private Integer pageNum; //
    private Integer pageSize; //
    private String username; //
    private String nickName; //
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime; //
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime; //
}
