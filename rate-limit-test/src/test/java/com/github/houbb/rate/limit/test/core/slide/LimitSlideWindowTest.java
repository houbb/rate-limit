package com.github.houbb.rate.limit.test.core.slide;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.rate.limit.core.bs.RateLimitBs;
import com.github.houbb.rate.limit.core.core.IRateLimit;
import com.github.houbb.rate.limit.core.core.impl.RateLimitSlideWindow;
import com.github.houbb.rate.limit.test.core.LimitCountSlideWindowTest;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
public class LimitSlideWindowTest {

    private static final Log log = LogFactory.getLog(LimitCountSlideWindowTest.class);

    /**
     * @since 0.0.5
     */
    private static final IRateLimit LIMIT = RateLimitBs.newInstance()
            .interval(2)
            .count(2)
            .limitClass(RateLimitSlideWindow.class)
            .build();

    static class LimitRunnable implements Runnable {

        @Override
        public void run() {
            for(int i = 0; i < 6; i++) {
                LIMIT.tryAcquire();
                log.info("{}-{}", Thread.currentThread().getName(), i);
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new LimitRunnable()).start();
        new Thread(new LimitRunnable()).start();
    }

}
