/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-acquire All rights reserved.
 */

package com.github.houbb.rate.limit.core.core;

import org.apiguardian.api.API;

/**
 *
 * 限流核心接口
 *
 * 后续可以添加 tryAcquire 等方法，指定等待的时间等。
 * @author bbhou
 * @since 0.0.1
 * @since 0.0.1
 */
@API(status = API.Status.MAINTAINED)
public interface IRateLimit {

    /**
     * 尝试获取锁
     * @since 0.0.5
     * @return 是否获取到锁
     */
    boolean acquire() ;

    /**
     * 释放锁
     * @since 0.0.5
     */
    void release();

}
