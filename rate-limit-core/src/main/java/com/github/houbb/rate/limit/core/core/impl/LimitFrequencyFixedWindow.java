/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-limit All rights reserved.
 */

package com.github.houbb.rate.limit.core.core.impl;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.rate.limit.core.core.ILimit;
import com.github.houbb.rate.limit.core.core.ILimitContext;
import com.github.houbb.rate.limit.core.util.ExecutorServiceUtil;
import org.apiguardian.api.API;

import java.util.concurrent.CountDownLatch;

/**
 * 全局的限制频率
 *
 * 固定时间窗口
 * @author houbinbin
 * Created by bbhou on 2017/9/20.
 * @since 0.0.4
 */
@API(status = API.Status.EXPERIMENTAL)
public class LimitFrequencyFixedWindow implements ILimit {

    /**
     * 日志
     * @since 0.0.4
     */
    private static final Log LOG = LogFactory.getLog(LimitFrequencyFixedWindow.class);

    /**
     * 上下文
     * @since 0.0.4
     */
    private final ILimitContext context;

    /**
     * 状态
     * @since 0.0.4
     */
    private volatile boolean status = false;

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
    public LimitFrequencyFixedWindow(ILimitContext context) {
        this.context = context;

        // 定时将 count 清零。
        ExecutorServiceUtil.singleSchedule(new Runnable() {
            @Override
            public void run() {
                initCounter();
            }
        }, context.interval(), context.timeUnit());
    }

    @Override
    public synchronized void limit() {
        if(status) {
            try {
                LOG.debug("[Limit] fixed frequency need wait for notify.");
                latch.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOG.debug("[Limit] fixed frequency is interrupt", e);
            }
        }

        // 设置为真
        this.status = true;
        this.latch = new CountDownLatch(1);
    }

    /**
     * 初始化计数器
     * @since 0.0.4
     */
    private void initCounter() {
        this.status = false;

        // 通知可以继续执行
        latch.countDown();
        LOG.info("[Limit] fixed frequency notify all");
    }

}
