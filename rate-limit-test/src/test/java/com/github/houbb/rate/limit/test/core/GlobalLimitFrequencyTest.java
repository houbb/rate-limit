/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-limit All rights reserved.
 */

package com.github.houbb.rate.limit.test.core;

import com.github.houbb.rate.limit.core.core.Limit;
import com.github.houbb.rate.limit.core.core.impl.ThreadLocalLimitFrequency;

import java.util.concurrent.TimeUnit;

/**
 * Created by bbhou on 2017/11/2.
 */
public class GlobalLimitFrequencyTest {

    private static Limit limit = new ThreadLocalLimitFrequency(TimeUnit.SECONDS, 2);

    static class LimitRunnable implements Runnable {

        @Override
        public void run() {
            for(int i = 0; i < 5; i++) {
                limit.limit();
                System.out.println("LimitRunnable");
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new LimitRunnable()).start();
    }

}
