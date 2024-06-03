package com.XZY.mallchat.common.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 * Author:戏中言
 * 分布式锁注解
 * @date: 2024/4/3 15:11
 */

@Retention(RetentionPolicy.RUNTIME)//注解在运行的时候生效
@Target(ElementType.METHOD)
public @interface RedissionLock {
    /**
     * key的前缀，默认取方法全限定名字，可以自己指定
     * @return
     */
    String prefixKey() default "";


    /**
     * 支持sprtingEl表达式的key
     */

    String key();

    /**
     * 等待锁的排队时间 默认快速失败
     */
    int waitTime() default -1;

    /**
     * 时间单位，默认毫秒
     */
    TimeUnit unit() default TimeUnit.MILLISECONDS;

}
