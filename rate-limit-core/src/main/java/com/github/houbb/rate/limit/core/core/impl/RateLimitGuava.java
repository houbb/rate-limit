/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-tryAcquire All rights reserved.
 */

package com.github.houbb.rate.limit.core.core.impl;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.rate.limit.core.core.IRateLimitContext;
import com.google.common.util.concurrent.RateLimiter;
import org.apiguardian.api.API;

import java.util.concurrent.TimeUnit;

/**
 * 基于 guava 的实现
 *
 * 固定时间窗口
 * @author houbinbin
 * Created by bbhou on 2017/9/20.
 * @since 0.0.9
 */
@API(status = API.Status.EXPERIMENTAL)
public class RateLimitGuava extends RateLimitAdaptor {

    /**
     * 日志
     * @since 0.0.5
     */
    private static final Log LOG = LogFactory.getLog(RateLimitGuava.class);

    /**
     * 上下文
     * @since 0.0.5
     */
    private final IRateLimitContext context;

    /**
     * 速率限制器
     * @since 0.0.9
     */
    private final RateLimiter rateLimiter;

    /**
     * 构造器
     * @param context 上下文
     * @since 0.0.5
     */
    public RateLimitGuava(IRateLimitContext context) {
        this.context = context;

        // 暂不考虑特殊输入，比如 1s 令牌少于1 的场景
        long intervalSeconds = context.timeUnit().toSeconds(context.interval());
        double rate = context.count() / (intervalSeconds * 1.0);
        this.rateLimiter = RateLimiter.create(rate);
    }

    @Override
    public boolean tryAcquire() {
        return rateLimiter.tryAcquire();
    }

    @Override
    public boolean tryAcquire(int permits, long timeout, TimeUnit timeUnit) {
        return rateLimiter.tryAcquire(permits, timeout, timeUnit);
    }

    @Override
    public double acquire(int permits) {
        return rateLimiter.acquire(permits);
    }

    @Override
    public double acquire() {
        return rateLimiter.acquire();
    }

}
