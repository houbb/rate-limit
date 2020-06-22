///*
// * Copyright (c)  2018. houbinbin Inc.
// * rate-LIMIT All rights reserved.
// */
//
//package com.github.houbb.rate.limit.test.core;
//
//import com.github.houbb.log.integration.core.Log;
//import com.github.houbb.log.integration.core.LogFactory;
//import com.github.houbb.rate.limit.core.bs.LimitBs;
//import com.github.houbb.rate.limit.core.core.ILimit;
//
///**
// * 全局-限制访问频率
// * Created by bbhou on 2017/11/2.
// */
//public class LimitFrequencySlideWindowTest {
//
//    private static final Log log = LogFactory.getLog(LimitFrequencySlideWindowTest.class);
//
//    /**
//     * 2S 访问一次
//     * @since 0.0.3
//     */
//    private static final ILimit LIMIT = LimitBs.newInstance()
//            .interval(2)
//            .limit(LimitFrequencySlideWindows.class)
//            .build();
//
//    static class LimitRunnable implements Runnable {
//
//        @Override
//        public void run() {
//            for (int i = 0; i < 5; i++) {
//                LIMIT.acquire();
//                log.info("{}-{}", Thread.currentThread().getName(), i);
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        new Thread(new LimitRunnable()).start();
//    }
//
//}
