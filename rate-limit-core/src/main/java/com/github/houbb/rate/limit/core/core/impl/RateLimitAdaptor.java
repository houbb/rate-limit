package com.github.houbb.rate.limit.core.core.impl;

import com.github.houbb.rate.limit.core.core.IRateLimit;

import java.util.concurrent.TimeUnit;

/**
 * 适配器
 * @author binbin.hou
 * @since 0.0.5
 */
public class RateLimitAdaptor implements IRateLimit {

    @Override
    public boolean tryAcquire() {
        return false;
    }

    @Override
    public boolean tryAcquire(int permits, long timeout, TimeUnit timeUnit) {
        return false;
    }

    @Override
    public double acquire(int permits) {
        return 0;
    }

    @Override
    public double acquire() {
        return 0;
    }

}
