package com.XZY.mallchat.common.common.domain.dto;

import lombok.Data;

/**
 * 对ip和uid进行绑定
 * Description:
 * Author:戏中言
 *
 * @date: 2024/4/2 14:07
 */
@Data
public class RequestInfo {
    private Long uid;
    private String ip;
}
