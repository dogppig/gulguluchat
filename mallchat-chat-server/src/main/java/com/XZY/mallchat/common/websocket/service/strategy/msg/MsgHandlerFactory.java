package com.XZY.mallchat.common.websocket.service.strategy.msg;


import com.XZY.mallchat.common.common.utils.AssertUtil;
import com.abin.frequencycontrol.exception.CommonErrorEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:

 */
public class MsgHandlerFactory {
    private static final Map<Integer, AbstractMsgHandler> STRATEGY_MAP = new HashMap<>();

    public static void register(Integer code, AbstractMsgHandler strategy) {
        STRATEGY_MAP.put(code, strategy);
    }

    public static AbstractMsgHandler getStrategyNoNull(Integer code) {
        AbstractMsgHandler strategy = STRATEGY_MAP.get(code);
        AssertUtil.isNotEmpty(strategy, CommonErrorEnum.PARAM_VALID);
        return strategy;
    }
}
