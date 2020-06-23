/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-tryAcquire All rights reserved.
 */

package com.github.houbb.rate.limit.core.core.impl;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.rate.limit.core.core.IRateLimitContext;
import org.apiguardian.api.API;

import java.util.concurrent.Semaphore;

/**
 * 信号量
 * <p>
 * 通过控制信号量的速度，来控制并发。
 *
 * @author houbinbin
 * @since 0.0.5
 */
@API(status = API.Status.EXPERIMENTAL)
public class RateLimitSemaphore extends RateLimitAdaptor {

    /**
     * 日志
     *
     * @since 0.0.5
     */
    private static final Log LOG = LogFactory.getLog(RateLimitSemaphore.class);

    /**
     * 信号量
     *
     * @since 0.0.5
     */
    private final Semaphore semaphore;

    /**
     * 构造器
     *
     * @param context 上下文
     * @since 0.0.5
     */
    public RateLimitSemaphore(final IRateLimitContext context) {
        this.semaphore = new Semaphore(context.count());
    }

    @Override
    public synchronized boolean tryAcquire() {
        try {
            LOG.debug("[Limit] start tryAcquire");
            this.semaphore.acquire(1);
            LOG.debug("[Limit] end tryAcquire");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOG.error("[Limit] semaphore meet ex: ", e);
            return false;
        }

        return true;
    }

    /**
     * 释放锁
     * @since 0.0.5
     */
    public void release() {
        LOG.debug("[Limit] start release");
        this.semaphore.release(1);
        LOG.debug("[Limit] end release");
    }

}
