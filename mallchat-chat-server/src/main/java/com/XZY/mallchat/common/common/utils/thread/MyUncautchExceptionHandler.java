package com.XZY.mallchat.common.common.utils.thread;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyUncautchExceptionHandler implements Thread.UncaughtExceptionHandler{

    /**
     * 在源码中如果异常没有被捕获就会进入这个方法这个是所有线程的方法
     * @param t
     * @param e
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.error("uncaughtException in thread",e);
    }


}
