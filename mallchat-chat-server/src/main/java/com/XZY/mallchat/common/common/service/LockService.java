package com.XZY.mallchat.common.common.service;

import com.abin.frequencycontrol.exception.BusinessException;
import com.abin.frequencycontrol.exception.CommonErrorEnum;
import lombok.SneakyThrows;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Description:
 * Author:戏中言
 *
 * @date: 2024/4/3 14:23
 */
@Service
public class LockService {
    @Autowired
    private RedissonClient redissonClient;

    @SneakyThrows
    public <T> T excuteWithLock (String key, int waitTime, TimeUnit timeUnit , Supplier<T> supplier){
        RLock rLock = redissonClient.getLock(key);
        boolean success = rLock.tryLock(waitTime,timeUnit);
        if(!success){
            throw new BusinessException(CommonErrorEnum.FREQUENCY_LIMIT);
        }

        try {
        return supplier.get();//运行传入的lamabda表达式
        }finally {
            rLock.unlock();
        }
    }

    public <T> T excuteWithLock (String key,Supplier<T> supplier){
        return excuteWithLock(key,-1,TimeUnit.MILLISECONDS,supplier);
    }


    public <T> T excuteWithLock (String key,Runnable runnable){
        return excuteWithLock(key,-1,TimeUnit.MILLISECONDS,()->
        {
            runnable.run();
            return null;
        });
    }
    @FunctionalInterface
    public interface Supplier<T> {

        /**
         * Gets a result.
         *
         * @return a result
         */
        T get() throws Throwable;
    }



}
