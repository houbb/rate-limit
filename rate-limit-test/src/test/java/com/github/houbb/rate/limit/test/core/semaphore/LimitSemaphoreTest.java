package com.github.houbb.rate.limit.test.core.semaphore;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.rate.limit.core.bs.RateLimitBs;
import com.github.houbb.rate.limit.core.core.impl.RateLimitSemaphore;

import java.util.concurrent.TimeUnit;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
public class LimitSemaphoreTest {

    private static final Log LOG = LogFactory.getLog(LimitSemaphoreTest.class);

    private static final RateLimitSemaphore LIMIT = (RateLimitSemaphore) RateLimitBs.newInstance(RateLimitSemaphore.class)
            .count(1)
            .build();

    static class LimitRunnable implements Runnable {
        @Override
        public void run() {
            for(int i = 0; i < 2; i++) {
                try {
                    LIMIT.tryAcquire();
                    LOG.info("{}-{}", Thread.currentThread().getName(), i);
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    LIMIT.release();
                }
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new LimitRunnable()).start();
        new Thread(new LimitRunnable()).start();
    }

}
