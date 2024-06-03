package com.XZY.mallchat.common.common.aspect;

import com.XZY.mallchat.common.common.annotation.RedissionLock;
import com.XZY.mallchat.common.common.service.LockService;
import com.XZY.mallchat.common.common.utils.SpElUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.RedissonLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Description:
 * Author:戏中言
 *
 * @date: 2024/4/3 15:20
 */
@Component
@Aspect
@Order(0)//确保比事务锁先执行  分布式锁一定要在事务外
public class RedissionLockAspect {
    @Autowired
    private LockService lockService;

    @Around("@annotation(redissonLock)")
    public Object around(ProceedingJoinPoint joinPoint, RedissionLock redissonLock){
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String prefix = StringUtils.isBlank(redissonLock.prefixKey())
                ? SpElUtils.getMethodKey(method) : redissonLock.prefixKey();
        String key = SpElUtils.parseSpEl(method, joinPoint.getArgs(), redissonLock.key());
        return lockService.excuteWithLock(prefix + key, redissonLock.waitTime(), redissonLock.unit(), ()-> joinPoint.proceed());
    }

}
