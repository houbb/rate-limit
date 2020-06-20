/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-limit All rights reserved.
 */

package com.github.houbb.rate.limit.core.core.impl;



import com.github.houbb.rate.limit.core.core.Limit;

import org.apiguardian.api.API;

import java.util.concurrent.TimeUnit;

/**
 * 1. 线程安全问题
 * 2. 如果时间间隔太长，需要保存问题 (ehcache)
 * 3. 计时器问题 (StopWatch)
 *
 * 1. 对于每一个线程都有对应的计时基准。
 *
 * @author bbhou
 * @since 0.0.1
 */
@API(status = API.Status.EXPERIMENTAL)
public class ThreadLocalLimitFrequency implements Limit {

    /**
     * 时间单位
     */
    private TimeUnit timeUnit;

    /**
     * 时间间隔
     */
    private long interval;

    /**
     * 构造器
     * @param timeUnit 时间单位
     * @param interval 时间间隔
     */
    public ThreadLocalLimitFrequency(TimeUnit timeUnit, long interval) {
        this.timeUnit = timeUnit;
        this.interval = interval;
    }

    @Override
    public void limit() {
        AbstractLimitFrequency abstractLimitFrequency = threadLocal.get();
        abstractLimitFrequency.limit();
    }


    /**
     * 线程
     * 1. 保证每一个线程都有一份独立的线程
     */
    private ThreadLocal<AbstractLimitFrequency> threadLocal = new ThreadLocal<AbstractLimitFrequency>(){

        @Override
        protected synchronized AbstractLimitFrequency initialValue() {
            return new GlobalLimitFrequency(timeUnit, interval);
        }


    };

}
