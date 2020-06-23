/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-LIMIT All rights reserved.
 */

package com.github.houbb.rate.limit.test.core;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.rate.limit.core.bs.RateLimitBs;
import com.github.houbb.rate.limit.core.core.IRateLimit;
import com.github.houbb.rate.limit.core.core.impl.RateLimitFixedWindow;

/**
 * 全局-限制调用次数案例
 * Created by bbhou on 2017/11/2.
 * @since 0.0.4
 */
public class LimitCountFixedWindowTest {

    private static final Log log = LogFactory.getLog(LimitCountFixedWindowTest.class);

    /**
     * 2S 内最多运行 5 次
     * @since 0.0.5
     */
    private static final IRateLimit LIMIT = RateLimitBs.newInstance()
            .interval(5)
            .count(5)
            .limitClass(RateLimitFixedWindow.class)
            .build();

    static class LimitRunnable implements Runnable {
        @Override
        public void run() {
            for(int i = 0; i < 10; i++) {
                LIMIT.tryAcquire();
                log.info("{}-{}", Thread.currentThread().getName(), i);
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new LimitRunnable()).start();
    }

}
