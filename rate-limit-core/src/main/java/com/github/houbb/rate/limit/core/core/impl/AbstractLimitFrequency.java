/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-limit All rights reserved.
 */

package com.github.houbb.rate.limit.core.core.impl;


import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.rate.limit.core.core.Limit;
import com.github.houbb.rate.limit.core.support.IsFirstTime;
import com.github.houbb.rate.limit.core.support.LimitHandler;
import com.github.houbb.rate.limit.core.support.TimeDiff;
import com.github.houbb.rate.limit.core.support.impl.DefaultLimitHandler;
import com.github.houbb.rate.limit.core.support.impl.SimpleIsFirstTime;
import com.github.houbb.rate.limit.core.support.impl.SimpleTimeDiff;
import org.apiguardian.api.API;

import java.util.concurrent.TimeUnit;

/**
 * 1. 线程安全问题
 * 2. 如果时间间隔太长，需要保存问题 (ehcache)
 * 3. 计时器问题 (StopWatch)
 *
 * 9223372036854775807 LONG 的最大值。不用关心太大的问题。
 *
 * @author bbhou
 * @date 2017/9/20
 */
@API(status = API.Status.INTERNAL)
public abstract class AbstractLimitFrequency implements Limit {

    /**
     * 时间单位
     */
    protected TimeUnit timeUnit;

    /**
     * 时间间隔
     */
    protected long interval;

    /**
     * 间隔毫秒数;
     */
    protected  final long intervalInMills;


    //--------------------------------------------- support
    /**
     * 是否为第一次调用
     */
    private IsFirstTime isFirstTime;

    /**
     * 获取2次时间间隔
     * 1. 将此变量设置为静态的。
     */
    private TimeDiff timeDiff;

    /**
     * 超时限制的处理器
     */
    private LimitHandler limitHandler;


    public AbstractLimitFrequency(TimeUnit timeUnit, long interval) {
        this.timeUnit = timeUnit;
        this.interval = interval;
        intervalInMills = timeUnit.toMillis(interval);
    }

    //--------------------------------------------- support

    public IsFirstTime getIsFirstTime() {
        if(ObjectUtil.isNull(isFirstTime)) {
            isFirstTime = new SimpleIsFirstTime();
        }
        return isFirstTime;
    }

    public void setIsFirstTime(IsFirstTime isFirstTime) {
        this.isFirstTime = isFirstTime;
    }

    public TimeDiff getTimeDiff() {
        if(ObjectUtil.isNull(timeDiff)) {
            timeDiff = new SimpleTimeDiff();
        }
        return timeDiff;
    }

    public void setTimeDiff(TimeDiff timeDiff) {
        this.timeDiff = timeDiff;
    }

    public LimitHandler getLimitHandler() {
        if(ObjectUtil.isNull(limitHandler)) {
            limitHandler = new DefaultLimitHandler();
        }
        return limitHandler;
    }

    public void setLimitHandler(LimitHandler limitHandler) {
        this.limitHandler = limitHandler;
    }

}
