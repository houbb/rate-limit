package com.github.houbb.rate.limit.test.core.slide;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.rate.limit.core.bs.LimitBs;
import com.github.houbb.rate.limit.core.core.ILimit;
import com.github.houbb.rate.limit.core.core.impl.LimitSlideWindow;
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
    private static final ILimit LIMIT = LimitBs.newInstance()
            .interval(2)
            .count(2)
            .limit(LimitSlideWindow.class)
            .build();

    static class LimitRunnable implements Runnable {

        @Override
        public void run() {
            for(int i = 0; i < 6; i++) {
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
