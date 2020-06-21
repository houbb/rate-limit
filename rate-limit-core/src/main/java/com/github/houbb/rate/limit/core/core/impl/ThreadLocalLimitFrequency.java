///*
// * Copyright (c)  2018. houbinbin Inc.
// * rate-limit All rights reserved.
// */
//
//package com.github.houbb.rate.limit.core.core.impl;
//
//
//
//import com.github.houbb.rate.limit.core.core.ILimit;
//
//import com.github.houbb.rate.limit.core.core.ILimitContext;
//import org.apiguardian.api.API;
//
///**
// * 1. 线程安全问题
// * 2. 如果时间间隔太长，需要保存问题 (ehcache)
// * 3. 计时器问题 (StopWatch)
// *
// * 1. 对于每一个线程都有对应的计时基准。
// *
// * @author bbhou
// * @since 0.0.1
// */
//@API(status = API.Status.EXPERIMENTAL)
//public class ThreadLocalLimitFrequency implements ILimit {
//
//    private final ILimitContext context;
//
//    public ThreadLocalLimitFrequency(ILimitContext context) {
//        this.context = context;
//    }
//
//    @Override
//    public void limit() {
//        ILimit limit = threadLocal.get();
//        limit.limit();
//    }
//
//    /**
//     * 线程
//     * 1. 保证每一个线程都有一份独立的线程
//     */
//    private ThreadLocal<ILimit> threadLocal = new ThreadLocal<ILimit>(){
//
//        @Override
//        protected synchronized ILimit initialValue() {
//            return new LimitFrequencySlide(context);
//        }
//
//
//    };
//
//}
