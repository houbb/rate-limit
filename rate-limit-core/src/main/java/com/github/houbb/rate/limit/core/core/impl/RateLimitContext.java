package com.github.houbb.rate.limit.core.core.impl;

import com.github.houbb.rate.limit.core.core.IRateLimitContext;

import java.util.concurrent.TimeUnit;

/**
 * <p> project: rate-acquire-RateLimitContext </p>
 * <p> create on 2020/6/20 21:35 </p>
 *
 * @author binbin.hou
 * @since 0.0.3
 */
public class RateLimitContext implements IRateLimitContext {

    /**
     * 单位
     * @since 0.0.3
     */
    private TimeUnit timeUnit;

    /**
     * 时间间隔
     * @since 0.0.3
     */
    private long interval;

    /**
     * 次数
     * @since 0.0.3
     */
    private int count;

    public static RateLimitContext newInstance() {
        return new RateLimitContext();
    }

    @Override
    public TimeUnit timeUnit() {
        return timeUnit;
    }

    public RateLimitContext timeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
        return this;
    }

    @Override
    public long interval() {
        return interval;
    }

    public RateLimitContext interval(long interval) {
        this.interval = interval;
        return this;
    }

    @Override
    public int count() {
        return count;
    }

    public RateLimitContext count(int count) {
        this.count = count;
        return this;
    }

}
