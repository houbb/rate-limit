/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-acquire All rights reserved.
 */

package com.github.houbb.rate.limit.core.core.impl;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.rate.limit.core.core.IRateLimitContext;
import com.github.houbb.rate.limit.core.util.ExecutorServiceUtil;
import org.apiguardian.api.API;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 滑动窗口限制次数
 *
 * 1. 限制 queue 的大小与 count 一致
 * 2.
 * @author houbinbin
 * Created by bbhou on 2017/9/20.
 * @since 0.0.5
 */
@API(status = API.Status.EXPERIMENTAL)
public class RateLimitSlideWindow extends RateLimitAdaptor {

    private static final Log LOG = LogFactory.getLog(RateLimitSlideWindow.class);

    /**
     * 默认切分为10个窗口
     *
     * 后期可以考虑更加灵活的配置，暂时写死。
     * @since 0.0.5
     */
    private static final int WINDOW_NUM = 10;

    /**
     * 用于存放时间的队列
     * @since 0.0.3
     */
    private final long[] array;

    /**
     * 最大的限制数量
     * @since 0.0.5
     */
    private final IRateLimitContext limitContext;

    /**
     * 阻塞执行的阻断器
     * @since 0.0.5
     */
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    /**
     * 滑动的下标
     * @since 0.0.5
     */
    private volatile int index = 0;

    /**
     * 计数器
     * @since 0.0.5
     */
    private final AtomicLong counter = new AtomicLong(0);

    /**
     * 是否需要 count down
     * @since 0.0.5
     */
    private volatile boolean isNeedCountDown = false;
    /**
     * 构造器
     * @param context 上下文
     * @since 0.0.4
     */
    public RateLimitSlideWindow(final IRateLimitContext context) {
        this.limitContext = context;
        this.array = new long[WINDOW_NUM];

        // 定期清理列表的第一个元素
        long intervalMills = context.timeUnit().toMillis(context.interval());
        long timeWindow = intervalMills / WINDOW_NUM;
        ExecutorServiceUtil.singleSchedule(new SlideWindowTask(), timeWindow, timeWindow, TimeUnit.MILLISECONDS);
    }

    /**
     * 滑动窗口的定时任务
     * @since 0.0.5
     */
    private class SlideWindowTask implements Runnable {
        @Override
        public synchronized void run() {
            // 清空队首的元素
            index = (index + 1) % WINDOW_NUM;

            // 这里是一个时间窗口对应的值
            long value = array[index];
            array[index] = 0;
            long newValue = counter.addAndGet(-value);

            LOG.debug("[Limit] slide window start with value: " + newValue);
            // 清空阻塞的状态
            if(isNeedCountDown && newValue < limitContext.count()) {
                LOG.debug("[Limit] clear count, notify start");
                countDownLatch.countDown();
                isNeedCountDown = false;
                LOG.debug("[Limit] clear count, notify end");
            }
        }
    }

    /**
     * 获取锁
     * @since 0.0.5
     */
    @Override
    public synchronized boolean acquire() {
        long currentSum = counter.get();
        if(currentSum > limitContext.count()) {
            // 循环等待
            try {
                LOG.debug("[Limit] wait for notify start");
                isNeedCountDown = true;
                countDownLatch.await();
                LOG.debug("[Limit] wait for notify end");
                this.countDownLatch = new CountDownLatch(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOG.error("[Limit] slide windows meet ex", e);
                return false;
            }
        }

        // 更新当前值
        array[index] = array[index]+1;
        counter.incrementAndGet();
        LOG.info("[Limit] current counter is " + counter.get());
        return true;
    }

}
