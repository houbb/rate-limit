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
//import com.github.houbb.rate.limit.core.support.IIsFirstTime;
//import com.github.houbb.rate.limit.core.support.ILimitHandler;
//import com.github.houbb.rate.limit.core.support.ITimeDiff;
//import com.github.houbb.rate.limit.core.support.impl.LimitHandler;
//import com.github.houbb.rate.limit.core.support.impl.IsFirstTime;
//import com.github.houbb.rate.limit.core.support.impl.TimeDiff;
//import org.apiguardian.api.API;
//
//import java.util.concurrent.TimeUnit;
//
///**
// * 1. 线程安全问题
// * 2. 如果时间间隔太长，需要保存问题 (ehcache)
// * 3. 计时器问题 (StopWatch)
// *
// * 9223372036854775807 LONG 的最大值。不用关心太大的问题。
// *
// * @author bbhou
// * @since 0.0.1
// */
//@API(status = API.Status.INTERNAL)
//public abstract class AbstractLimitFrequency implements ILimit {
//
//    /**
//     * 时间单位
//     */
//    protected TimeUnit timeUnit;
//
//    /**
//     * 时间间隔
//     */
//    protected long interval;
//
//    /**
//     * 间隔毫秒数;
//     */
//    protected  final long intervalInMills;
//
//
//    //--------------------------------------------- support
//    /**
//     * 是否为第一次调用
//     */
//    private IIsFirstTime isFirstTime;
//
//    /**
//     * 获取2次时间间隔
//     * 1. 将此变量设置为静态的。
//     */
//    private ITimeDiff timeDiff;
//
//    /**
//     * 超时限制的处理器
//     */
//    private ILimitHandler limitHandler;
//
//
//    public AbstractLimitFrequency(TimeUnit timeUnit, long interval) {
//        this.timeUnit = timeUnit;
//        this.interval = interval;
//        intervalInMills = timeUnit.toMillis(interval);
//    }
//
//    //--------------------------------------------- support
//
//    public IIsFirstTime getIsFirstTime() {
//        if(ObjectUtil.isNull(isFirstTime)) {
//            isFirstTime = new IsFirstTime();
//        }
//        return isFirstTime;
//    }
//
//    public void setIsFirstTime(IIsFirstTime isFirstTime) {
//        this.isFirstTime = isFirstTime;
//    }
//
//    public ITimeDiff getTimeDiff() {
//        if(ObjectUtil.isNull(timeDiff)) {
//            timeDiff = new TimeDiff();
//        }
//        return timeDiff;
//    }
//
//    public void setTimeDiff(ITimeDiff timeDiff) {
//        this.timeDiff = timeDiff;
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
