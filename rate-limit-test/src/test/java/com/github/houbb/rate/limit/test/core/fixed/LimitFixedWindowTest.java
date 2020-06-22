/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-LIMIT All rights reserved.
 */

package com.github.houbb.rate.limit.test.core.fixed;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.rate.limit.core.bs.LimitBs;
import com.github.houbb.rate.limit.core.core.ILimit;
import com.github.houbb.rate.limit.core.core.impl.LimitFixedWindow;

/**
 * 限制调用-固定时间窗口案例
 * Created by bbhou on 2017/11/2.
 * @since 0.0.4
 */
public class LimitFixedWindowTest {

    private static final Log log = LogFactory.getLog(LimitFixedWindowTest.class);

    /**
     * 1S 内最多运行 1 次
     * @since 0.0.5
     */
    private static final ILimit LIMIT = LimitBs.newInstance()
            .interval(1)
            .count(1)
            .limit(LimitFixedWindow.class)
            .build();

    static class LimitRunnable implements Runnable {
        @Override
        public void run() {
            for(int i = 0; i < 3; i++) {
                LIMIT.acquire();
                log.info("{}-{}", Thread.currentThread().getName(), i);
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new LimitRunnable()).start();
        new Thread(new LimitRunnable()).start();
    }

}
