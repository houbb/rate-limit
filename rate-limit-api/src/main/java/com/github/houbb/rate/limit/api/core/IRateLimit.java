/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-tryAcquire All rights reserved.
 */

package com.github.houbb.rate.limit.api.core;

/**
 * 限流核心接口
 * 后续可以添加 tryAcquire 等方法，指定等待的时间等。
 *
 * @author bbhou
 * @since 0.0.1
 */
public interface IRateLimit {

    /**
     * 尝试获取指定时间的锁
     * @param context 上下文
     * @return 是否获取到锁
     * @since 1.0.0
     */
    boolean tryAcquire(final IRateLimitContext context);


}
