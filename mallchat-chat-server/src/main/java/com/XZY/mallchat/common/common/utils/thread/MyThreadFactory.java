package com.XZY.mallchat.common.common.utils.thread;


import lombok.AllArgsConstructor;

import java.util.concurrent.ThreadFactory;

/**
 * 这里是装饰类 反射做装饰
 * 这个类是标准的装饰器模式
 */
@AllArgsConstructor
public class MyThreadFactory implements ThreadFactory {
    public static final MyUncautchExceptionHandler MY_UNCAUTCH_EXCEPTION_HANDLER = new MyUncautchExceptionHandler();
    private ThreadFactory original;//
    @Override
    public Thread newThread(Runnable r) {
        Thread thread =original.newThread(r);
        thread.setUncaughtExceptionHandler(MY_UNCAUTCH_EXCEPTION_HANDLER);
        return thread;
    }
}
