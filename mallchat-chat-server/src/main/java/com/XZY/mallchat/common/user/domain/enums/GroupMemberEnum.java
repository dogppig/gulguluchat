package com.XZY.mallchat.common.user.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Description:
 * Author:戏中言
 *
 * @date: 2024/5/24 19:53
 */
@AllArgsConstructor
@Getter
public enum GroupMemberEnum {
    QUNZHU(1, "群主"),
    GUANLI(2, "管理员"),
    PUTONG(3,"普通人")
    ;

    private final Integer id;
    private final String desc;

    private static Map<Integer, GroupMemberEnum> cache;

    static {
        cache = Arrays.stream(GroupMemberEnum.values()).collect(Collectors.toMap(GroupMemberEnum::getId, Function.identity()));
    }

    public static GroupMemberEnum of(Long id) {
        return cache.get(id);
    }
}
