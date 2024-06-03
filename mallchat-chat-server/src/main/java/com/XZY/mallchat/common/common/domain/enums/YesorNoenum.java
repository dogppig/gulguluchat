package com.XZY.mallchat.common.common.domain.enums;

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
 * @date: 2024/4/2 14:47
 */
@AllArgsConstructor
@Getter
public enum YesorNoenum {

    NOENUM(0,"否"),
    YESOR_NOENUM(1,"是"),
    ;
    private final Integer status;
    private final String desc;

    private static Map<Integer, YesorNoenum> cache;

    static {
        cache = Arrays.stream(YesorNoenum.values()).collect(Collectors.toMap(YesorNoenum::getStatus, Function.identity()));
    }

    public static YesorNoenum of(Integer type) {
        return cache.get(type);
    }

    public static Integer toStatus(Boolean bool) {
        return bool ? YESOR_NOENUM.getStatus() : NOENUM.getStatus();
    }

}
