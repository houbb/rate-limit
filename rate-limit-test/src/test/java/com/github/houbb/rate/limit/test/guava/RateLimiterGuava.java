package com.github.houbb.rate.limit.test.guava;

import com.google.common.util.concurrent.RateLimiter;
import org.junit.Test;

/**
 * @author binbin.hou
 * @since 0.0.9
 */
public class RateLimiterGuava {

    @Test
    public void limitTest() {
        RateLimiter limiter = RateLimiter.create(1);

        for(int i = 1; i < 5; i++) {
            double waitTime = limiter.acquire(i);
            System.out.println("cutTime=" + System.currentTimeMillis() + " acq:" + i + " waitTime:" + waitTime);
        }
    }

}
