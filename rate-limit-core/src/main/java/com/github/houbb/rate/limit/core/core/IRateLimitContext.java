/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-tryAcquire All rights reserved.
 */

package com.github.houbb.rate.limit.core.core;

import java.util.concurrent.TimeUnit;

/**
 *
 * 限流核心上下文接口
 * @author bbhou
 * @since 0.0.3
 */
public interface IRateLimitContext {

    /**
     * 单位
     * @return 单位
     * @since 0.0.3
     */
    TimeUnit timeUnit();

    /**
     * 时间间隔
     * @return 间隔
     * @since 0.0.3
     */
    long interval();

    /**
     * 次数
     * @return 次数
     * @since 0.0.3
     */
    int count();

}
