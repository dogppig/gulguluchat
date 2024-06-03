package com.XZY.mallchat.common.user.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Description:
 * Author:戏中言
 *
 * @date: 2024/4/3 13:41
 */

@AllArgsConstructor
@Getter
public enum IdempotentEum {

    UID(1,"uid"),
    MSG_ID(2,"消息Id");


    private final Integer type;//消息类型
    private  final String desc;//消息描述
}
