/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-tryAcquire All rights reserved.
 */

package com.github.houbb.rate.limit.core.core;

import org.apiguardian.api.API;

import java.util.concurrent.TimeUnit;

/**
 * 限流核心接口
 * <p>
 * 后续可以添加 tryAcquire 等方法，指定等待的时间等。
 *
 * @author bbhou
 * @since 0.0.1
 * @since 0.0.1
 */
@API(status = API.Status.MAINTAINED)
public interface IRateLimit {

    /**
     * 尝试获取锁
     * <p>
     * 1. 如果未获取到，立刻返回
     *
     * @return 是否获取到锁
     * @since 0.0.9
     */
    boolean tryAcquire();

    /**
     * 尝试获取指定时间的锁
     * @param permits 需要的令牌大小
     * @param timeout 超时时间
     * @param timeUnit 时间单位
     * @return 是否获取到锁
     * @since 0.0.9
     */
    boolean tryAcquire(int permits, long timeout, TimeUnit timeUnit);

    /**
     * 获取锁
     * 1. 一直等到锁为止
     * @param permits 需要的令牌大小
     * @return 等待的时间 mills
     * @since 0.0.9
     */
    double acquire(int permits);

    /**
     * 获取锁
     * 1. 令牌默认为1
     * @return 等待的时间 mills
     * @since 0.0.9
     */
    double acquire();

}
