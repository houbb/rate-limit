package com.github.houbb.rate.limit.core.core.impl;

import com.github.houbb.rate.limit.core.core.ILimitContext;
import com.github.houbb.rate.limit.core.support.ICurrentTime;
import com.github.houbb.rate.limit.core.support.IIsFirstTime;
import com.github.houbb.rate.limit.core.support.ILimitHandler;
import com.github.houbb.rate.limit.core.support.ITimeDiffer;

import java.util.concurrent.TimeUnit;

/**
 * <p> project: rate-acquire-LimitContext </p>
 * <p> create on 2020/6/20 21:35 </p>
 *
 * @author binbin.hou
 * @since 0.0.3
 */
public class LimitContext implements ILimitContext {

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

    public static LimitContext newInstance() {
        return new LimitContext();
    }

    @Override
    public TimeUnit timeUnit() {
        return timeUnit;
    }

    public LimitContext timeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
        return this;
    }

    @Override
    public long interval() {
        return interval;
    }

    public LimitContext interval(long interval) {
        this.interval = interval;
        return this;
    }

    @Override
    public int count() {
        return count;
    }

    public LimitContext count(int count) {
        this.count = count;
        return this;
    }

}
