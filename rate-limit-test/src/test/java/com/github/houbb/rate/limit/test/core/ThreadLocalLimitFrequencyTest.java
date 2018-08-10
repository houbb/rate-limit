/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-limit All rights reserved.
 */

package com.github.houbb.rate.limit.test.core;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.rate.limit.core.core.Limit;
import com.github.houbb.rate.limit.core.core.impl.GlobalLimitFrequency;
import com.github.houbb.rate.limit.core.core.impl.ThreadLocalLimitFrequency;

import java.util.concurrent.TimeUnit;

/**
 * ThreadLocal-限制访问频率
 * Created by bbhou on 2017/11/2.
 */
public class ThreadLocalLimitFrequencyTest {

    private static final Log log = LogFactory.getLog(ThreadLocalLimitFrequencyTest.class);

    /**
     * 2S 访问一次
     */
    private static Limit LIMIT = new ThreadLocalLimitFrequency(TimeUnit.SECONDS, 2);

    static class LimitRunnable implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 4; i++) {
                LIMIT.limit();
                log.info("{}-{}", Thread.currentThread().getName(), i);
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new LimitRunnable()).start();
        new Thread(new LimitRunnable()).start();
    }

}
