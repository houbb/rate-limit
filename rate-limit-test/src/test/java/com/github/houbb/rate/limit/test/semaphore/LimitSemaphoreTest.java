package com.github.houbb.rate.limit.test.semaphore;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.rate.limit.core.bs.LimitBs;
import com.github.houbb.rate.limit.core.core.ILimit;
import com.github.houbb.rate.limit.core.core.impl.LimitSemaphore;

import java.util.concurrent.TimeUnit;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
public class LimitSemaphoreTest {

    private static final Log LOG = LogFactory.getLog(LimitSemaphoreTest.class);

    private static final ILimit LIMIT = LimitBs.newInstance(LimitSemaphore.class)
            .count(1)
            .build();

    static class LimitRunnable implements Runnable {
        @Override
        public void run() {
            for(int i = 0; i < 2; i++) {
                try {
                    LIMIT.acquire();
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
