package com.XZY.mallchat.common.common.intercepter;

import cn.hutool.core.collection.CollectionUtil;
import com.XZY.mallchat.common.common.Excption.HttpErrorEnum;
import com.XZY.mallchat.common.common.utils.RequestHolderUtils;
import com.XZY.mallchat.common.user.domain.enums.BlackTypeEnum;
import com.XZY.mallchat.common.user.service.cache.Usercache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @description: 黑名单拦截器
 * @version: 1.0
 */

@Component
public class BlackInterceptor implements HandlerInterceptor {

    @Autowired
    private Usercache userCache;

//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        RequestUserInfo requestUserInfo = RequestHolderUtils.get();
//        Map<Integer, Set<String>> blackMap = userCache.getBlackMap();
//        boolean inBlackListJudgeUID = inBlackList(requestUserInfo.getUid(), blackMap.get(BlackTypeEnum.UID.getType()));
//        if (inBlackListJudgeUID) {
//            HttpErrorEnum.ACCESS_DENIED.sendHttpError(response);
//            return false;
//        }
//        boolean inBlackListJudgeIP = inBlackList(requestUserInfo.getUid(), blackMap.get(BlackTypeEnum.IP.getType()));
//        if (inBlackListJudgeIP) {
//            HttpErrorEnum.ACCESS_DENIED.sendHttpError(response);
//            return false;
//        }
//        return true;
//    }

    private boolean inBlackList(Object target, Set<String> set) {
        if (Objects.isNull(target) || CollectionUtil.isEmpty(set)) {
            return false;
        }
        return set.contains(target.toString());
    }


}
