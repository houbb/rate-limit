/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-acquire All rights reserved.
 */

package com.github.houbb.rate.limit.core.core.impl;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.rate.limit.core.core.ILimitContext;
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
public class LimitSemaphore extends LimitAdaptor {

    /**
     * 日志
     *
     * @since 0.0.5
     */
    private static final Log LOG = LogFactory.getLog(LimitSemaphore.class);

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
    public LimitSemaphore(final ILimitContext context) {
        this.semaphore = new Semaphore(context.count());
    }

    @Override
    public synchronized void acquire() {
        try {
            LOG.debug("[Limit] start acquire");
            this.semaphore.acquire(1);
            LOG.debug("[Limit] end acquire");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOG.error("[Limit] semaphore meet ex: ", e);
        }
    }

    @Override
    public void release() {
        LOG.debug("[Limit] start release");
        this.semaphore.release(1);
        LOG.debug("[Limit] end release");
    }

}
