package com.XZY.mallchat.common.user.domain.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Description:
 * Author:戏中言
 *
 * @date: 2024/4/5 10:01
 */

@Data
public class BlackReq {
    @NotNull
    @ApiModelProperty("拉黑目标uid")
    private Long uid;

}
