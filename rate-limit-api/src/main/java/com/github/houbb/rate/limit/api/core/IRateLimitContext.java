/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-tryAcquire All rights reserved.
 */

package com.github.houbb.rate.limit.api.core;

import com.github.houbb.common.cache.api.service.ICommonCacheService;
import com.github.houbb.rate.limit.api.support.*;
import com.github.houbb.timer.api.ITimer;

import java.lang.reflect.Method;

/**
 *
 * 限流核心上下文接口
 * @author bbhou
 * @since 1.0.0
 */
public interface IRateLimitContext {

    /**
     * 时间戳
     * @return 时间戳
     */
    ITimer timer();

    /**
     * 配置服务类
     * @return 服务类
     * @since 1.0.0
     */
    IRateLimitConfigService configService();

    /**
     * 方法服务类
     * @return 服务类
     * @since 1.0.0
     */
    IRateLimitMethodService methodService();

    /**
     * 标识服务
     * @return 服务
     * @since 1.0.0
     */
    IRateLimitTokenService tokenService();

    /**
     * 缓存服务
     * @return 统计服务
     */
    ICommonCacheService cacheService();

    /**
     * 拒绝时的监听策略
     * @return 策略
     * @since 1.0.0
     */
    IRateLimitRejectListener rejectListener();

    /**
     * 访问的方法
     * @return 方法
     */
    Method method();

    /**
     * 访问的方法参数
     * @return 方法参数
     */
    Object[] args();

    /**
     * 对应的缓存 key 命名空间
     * @return 结果
     * @since 1.1.0
     */
    String cacheKeyNamespace();

}
