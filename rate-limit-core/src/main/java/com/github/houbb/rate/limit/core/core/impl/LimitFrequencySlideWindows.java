/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-limit All rights reserved.
 */

package com.github.houbb.rate.limit.core.core.impl;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.rate.limit.core.core.ILimit;
import com.github.houbb.rate.limit.core.core.ILimitContext;
import com.github.houbb.rate.limit.core.support.IIsFirstTime;
import com.github.houbb.rate.limit.core.support.ILimitHandler;
import com.github.houbb.rate.limit.core.support.ITimeDiffer;

import org.apiguardian.api.API;

/**
 * 滑动窗口限制调用频率
 * 1. 线程安全问题
 * 2. 如果时间间隔太长，需要保存问题 (ehcache)
 * 3. 计时器问题 (StopWatch)
 * <p>This class is not thread-safe</p>
 *
 * @author houbinbin
 * Created by bbhou on 2017/9/20.
 * @since 0.0.1
 */
@API(status = API.Status.EXPERIMENTAL)
public class LimitFrequencySlideWindows implements ILimit {

    private static Log log = LogFactory.getLog(LimitFrequencySlideWindows.class);

    private final ILimitContext context;

    public LimitFrequencySlideWindows(ILimitContext context) {
        this.context = context;
    }

    @Override
    public synchronized void limit() {
        IIsFirstTime isFirstTime = context.isFirstTime();

        //1. 初次调用，可以考虑不进行时间拦截。
        //2. 计算本次和上次之间的时间间隔。如果时间 < 最小间隔。则睡眠等待。
        boolean isFirstTimeFlag = isFirstTime.isFirstTime();
        if (isFirstTimeFlag) {
            firstTimeHandler();
        } else {
            handleTimeDiff();
        }
    }

    /**
     * 初次调用处理
     * 1. 初始化第一次的时间调用
     */
    protected void firstTimeHandler() {
        ITimeDiffer timeDiff = context.timeDiffer();
        timeDiff.updateAfterCall();
    }

    /**
     * 处理时间差异
     */
    protected void handleTimeDiff() {
        ITimeDiffer timeDiff = context.timeDiffer();

        //1. 获取时间差
        long timeDiffInMills = timeDiff.getTimeDiff();
        long intervalInMills = context.timeUnit().toMillis(context.interval());
        //2. 时间差处理
        if (timeDiffInMills < intervalInMills) {
            long sleepInMills = intervalInMills - timeDiffInMills;
            ILimitHandler limitHandler = context.limitHandler();
            try {
                limitHandler.beforeHandle();
                limitHandler.handle(sleepInMills);
                limitHandler.afterHandle();
            } catch (Exception e) {
                log.error("GlobalLimitCount.limit() meet ex: "+e, e);
            }
        }

        //3. 更新时间
        timeDiff.updateAfterCall();
    }


}
