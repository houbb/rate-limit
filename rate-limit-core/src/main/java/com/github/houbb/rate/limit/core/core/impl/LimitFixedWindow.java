/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-acquire All rights reserved.
 */

package com.github.houbb.rate.limit.core.core.impl;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.rate.limit.core.core.ILimitContext;
import com.github.houbb.rate.limit.core.util.ExecutorServiceUtil;
import org.apiguardian.api.API;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 全局的限制次数
 *
 * 固定时间窗口
 * @author houbinbin
 * Created by bbhou on 2017/9/20.
 * @since 0.0.4
 */
@API(status = API.Status.EXPERIMENTAL)
public class LimitFixedWindow extends LimitAdaptor {

    /**
     * 日志
     * @since 0.0.4
     */
    private static final Log LOG = LogFactory.getLog(LimitFixedWindow.class);

    /**
     * 上下文
     * @since 0.0.4
     */
    private final ILimitContext context;

    /**
     * 计数器
     * @since 0.0.4
     */
    private AtomicInteger counter = new AtomicInteger(0);

    /**
     * 限制状态的工具
     *
     * 避免不同线程的 notify+wait 报错问题
     *
     * @since 0.0.4
     */
    private CountDownLatch latch = new CountDownLatch(1);

    /**
     * 构造器
     * @param context 上下文
     * @since 0.0.4
     */
    public LimitFixedWindow(ILimitContext context) {
        this.context = context;

        // 定时将 count 清零。
        final long interval = context.interval();
        final TimeUnit timeUnit = context.timeUnit();

        // 任务调度
        ExecutorServiceUtil.singleSchedule(new Runnable() {
            @Override
            public void run() {
                initCounter();
            }
        }, interval, timeUnit);
    }

    @Override
    public synchronized void acquire() {

        // 超过阈值，则进行等待
        if (counter.get() >= this.context.count()) {
            try {
                LOG.debug("[Limit] fixed count need wait for notify.");
                latch.await();
                LOG.debug("[Limit] fixed count need wait end ");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOG.error("[Limit] fixed count is interrupt", e);
            }
        }

        // 结束
        int value = this.counter.incrementAndGet();
        this.latch = new CountDownLatch(1);
        LOG.debug("[Limit] fixed count is " + value);
    }

    /**
     * 初始化计数器
     * @since 0.0.4
     */
    private void initCounter() {
        LOG.debug("[Limit] fixed count init counter start");

        // 通知可以继续执行（这里不能无脑 notify）会卡主
        if(this.counter.get() >= this.context.count()) {
            this.counter = new AtomicInteger(0);

            LOG.debug("[Limit] fixed count notify all start");
            latch.countDown();
            LOG.debug("[Limit] fixed count notify all end");
        }  else {
            this.counter = new AtomicInteger(0);
        }

    }

}
