/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-limit All rights reserved.
 */

package com.github.houbb.rate.limit.core.core.impl;

import org.apiguardian.api.API;

import java.util.concurrent.TimeUnit;

/**
 * 本地限制。
 * 1. 对于每一个线程都有对应的计时基准。
 *
 * @author bbhou
 * @since 0.0.1
 */
@API(status = API.Status.EXPERIMENTAL)
public class ThreadLocalLimitCount extends AbstractLimitCount {

    /**
     * 构造器
     * @param timeUnit 时间单位
     * @param interval 时间
     * @param count 访问次数
     */
    public ThreadLocalLimitCount(TimeUnit timeUnit, long interval, int count) {
        super(timeUnit, interval, count);
    }

    @Override
    public void limit() {
        GlobalLimitCount globalLimitCount = threadLocal.get();
        globalLimitCount.limit();
    }


    /**
     * 线程
     * 1. 保证每一个线程都有一份独立的线程
     */
    private ThreadLocal<GlobalLimitCount> threadLocal = new ThreadLocal<GlobalLimitCount>(){

        @Override
        protected synchronized GlobalLimitCount initialValue() {
            return new GlobalLimitCount(timeUnit, interval, count);
        }

    };


}
