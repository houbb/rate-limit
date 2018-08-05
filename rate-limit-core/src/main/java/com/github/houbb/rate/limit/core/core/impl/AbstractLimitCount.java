/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-limit All rights reserved.
 */

package com.github.houbb.rate.limit.core.core.impl;


import com.github.houbb.rate.limit.core.core.Limit;
import com.github.houbb.rate.limit.core.support.CurrentTime;
import com.github.houbb.rate.limit.core.support.LimitHandler;
import com.github.houbb.rate.limit.core.support.impl.DefaultLimitHandler;
import com.github.houbb.rate.limit.core.support.impl.SimpleCurrentTime;
import com.github.houbb.rate.limit.core.util.ArgUtil;

import org.apiguardian.api.API;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 在一定的时间内只能调用一个方法多少次。
 *
 * @author bbhou
 * @date 2017/9/21
 */
@API(status = API.Status.INTERNAL)
public abstract class AbstractLimitCount implements Limit {

    /**
     * 时间单位
     */
    protected TimeUnit timeUnit;

    /**
     * 时间长度间隔
     */
    protected long interval;

    /**
     * 总数限制
     */
    protected int count;

    /**
     * 用于存放时间的队列
     */
    protected BlockingQueue<Long> timeBlockQueue;

    /**
     * 间隔毫秒数;
     */
    protected  final long intervalInMills;

    //--------------------------------------------- SUPPORT
    /**
     * 当前时间
     */
    private CurrentTime currentTime;

    /**
     * 超时限制的处理者
     */
    private LimitHandler limitHandler;



    public AbstractLimitCount(TimeUnit timeUnit, long interval, int count) {
        this.timeUnit = timeUnit;
        this.interval = interval;
        this.count = count;

        timeBlockQueue = new ArrayBlockingQueue<>(count);
        intervalInMills = timeUnit.toMillis(interval);
    }


    //--------------------------------------------------- SUPPORT Getter & Setter
    public CurrentTime getCurrentTime() {
        //设置默认的当前时间获取
        if(ArgUtil.isNull(this.currentTime)) {
            currentTime = new SimpleCurrentTime();
        }

        return currentTime;
    }

    public void setCurrentTime(CurrentTime currentTime) {
        this.currentTime = currentTime;
    }

    public LimitHandler getLimitHandler() {
        if(ArgUtil.isNull(limitHandler)) {
            limitHandler = new DefaultLimitHandler();
        }
        return limitHandler;
    }

    public void setLimitHandler(LimitHandler limitHandler) {
        this.limitHandler = limitHandler;
    }

}
