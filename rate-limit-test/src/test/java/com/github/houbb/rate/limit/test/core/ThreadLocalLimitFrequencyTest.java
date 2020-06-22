///*
// * Copyright (c)  2018. houbinbin Inc.
// * rate-acquire All rights reserved.
// */
//
//package com.github.houbb.rate.acquire.test.core;
//
//import com.github.houbb.log.integration.core.Log;
//import com.github.houbb.log.integration.core.LogFactory;
//import com.github.houbb.rate.acquire.core.bs.LimitBs;
//import com.github.houbb.rate.acquire.core.core.ILimit;
//import com.github.houbb.rate.acquire.core.core.impl.ThreadLocalLimitCount;
//import com.github.houbb.rate.acquire.core.core.impl.ThreadLocalLimitFrequency;
//import org.junit.Ignore;
//
//import java.util.concurrent.TimeUnit;
//
///**
// * ThreadLocal-限制访问频率
// * Created by bbhou on 2017/11/2.
// */
//@Ignore
//public class ThreadLocalLimitFrequencyTest {
//
//    private static final Log log = LogFactory.getLog(ThreadLocalLimitFrequencyTest.class);
//
//    /**
//     * 2S 访问一次
//     */
//    private static final ILimit LIMIT = LimitBs.newInstance()
//            .interval(2)
//            .acquire(ThreadLocalLimitFrequency.class)
//            .build();
//
//    static class LimitRunnable implements Runnable {
//
//        @Override
//        public void run() {
//            for (int i = 0; i < 4; i++) {
//                LIMIT.acquire();
//                log.info("{}-{}", Thread.currentThread().getName(), i);
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        new Thread(new LimitRunnable()).start();
//        new Thread(new LimitRunnable()).start();
//    }
//
//}
