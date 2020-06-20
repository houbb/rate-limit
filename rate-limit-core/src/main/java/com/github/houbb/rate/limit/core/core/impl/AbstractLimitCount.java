///*
// * Copyright (c)  2018. houbinbin Inc.
// * rate-limit All rights reserved.
// */
//
//package com.github.houbb.rate.limit.core.core.impl;
//
//
//import com.github.houbb.heaven.util.lang.ObjectUtil;
//import com.github.houbb.rate.limit.core.core.ILimit;
//import com.github.houbb.rate.limit.core.support.ICurrentTime;
//import com.github.houbb.rate.limit.core.support.ILimitHandler;
//import com.github.houbb.rate.limit.core.support.impl.LimitHandler;
//import com.github.houbb.rate.limit.core.support.impl.CurrentTime;
//import org.apiguardian.api.API;
//
//import java.util.concurrent.ArrayBlockingQueue;
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.TimeUnit;
//
///**
// * 在一定的时间内只能调用一个方法多少次。
// *
// * @author bbhou
// * @since 0.0.1
// */
//@API(status = API.Status.INTERNAL)
//public abstract class AbstractLimitCount implements ILimit {
//
////    /**
////     * 时间单位
////     */
////    protected TimeUnit timeUnit;
////
////    /**
////     * 时间长度间隔
////     */
////    protected long interval;
////
////    /**
////     * 总数限制
////     */
////    protected int count;
//
//    /**
//     * 用于存放时间的队列
//     */
//    protected BlockingQueue<Long> timeBlockQueue;
//
//    /**
//     * 间隔毫秒数;
//     */
//    protected  final long intervalInMills;
//
//    //--------------------------------------------- SUPPORT
//    /**
//     * 当前时间
//     */
//    private ICurrentTime currentTime;
//
//    /**
//     * 超时限制的处理者
//     */
//    private ILimitHandler limitHandler;
//
//
//
//    public AbstractLimitCount(TimeUnit timeUnit, long interval, int count) {
//        this.timeUnit = timeUnit;
//        this.interval = interval;
//        this.count = count;
//
//        timeBlockQueue = new ArrayBlockingQueue<>(count);
//        intervalInMills = timeUnit.toMillis(interval);
//    }
//
//
//    //--------------------------------------------------- SUPPORT Getter & Setter
//    public ICurrentTime getCurrentTime() {
//        //设置默认的当前时间获取
//        if(ObjectUtil.isNull(this.currentTime)) {
//            currentTime = new CurrentTime();
//        }
//
//        return currentTime;
//    }
//
//    public void setCurrentTime(ICurrentTime currentTime) {
//        this.currentTime = currentTime;
//    }
//
//    public ILimitHandler getLimitHandler() {
//        if(ObjectUtil.isNull(limitHandler)) {
//            limitHandler = new LimitHandler();
//        }
//        return limitHandler;
//    }
//
//    public void setLimitHandler(ILimitHandler limitHandler) {
//        this.limitHandler = limitHandler;
//    }
//
//}
