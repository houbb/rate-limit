/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-LIMIT All rights reserved.
 */

package com.github.houbb.rate.limit.test.core;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.rate.limit.core.core.Limit;
import com.github.houbb.rate.limit.core.core.impl.GlobalLimitCount;
import org.junit.Ignore;


import java.util.concurrent.TimeUnit;

/**
 * 全局-限制调用次数案例
 * Created by bbhou on 2017/11/2.
 */
@Ignore
public class GlobalLimitCountTest {

    private static final Log log = LogFactory.getLog(GlobalLimitCountTest.class);

    /**
     * 2S 内最多运行 5 次
     */
    private static final Limit LIMIT = new GlobalLimitCount(TimeUnit.SECONDS, 2, 5);

    static class LimitRunnable implements Runnable {

        @Override
        public void run() {
            for(int i = 0; i < 10; i++) {
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
