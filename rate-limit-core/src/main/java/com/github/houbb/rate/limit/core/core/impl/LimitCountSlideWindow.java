/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-acquire All rights reserved.
 */

package com.github.houbb.rate.limit.core.core.impl;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.rate.limit.core.core.ILimitContext;
import com.github.houbb.rate.limit.core.support.ICurrentTime;
import com.github.houbb.rate.limit.core.support.ILimitHandler;
import org.apiguardian.api.API;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 滑动窗口限制次数
 * @author houbinbin
 * Created by bbhou on 2017/9/20.
 * @since 0.0.1
 */
@Deprecated
@API(status = API.Status.EXPERIMENTAL)
public class LimitCountSlideWindow extends LimitAdaptor {

    private static Log log = LogFactory.getLog(LimitCountSlideWindow.class);

    /**
     * 上下文
     * @since 0.0.3
     */
    private final ILimitContext context;

    /**
     * 用于存放时间的队列
     * @since 0.0.3
     */
    private final BlockingQueue<Long> timeBlockQueue;

    /**
     * 构造器
     * @param context 上下文
     * @since 0.0.3
     */
    public LimitCountSlideWindow(ILimitContext context) {
        this.context = context;
        this.timeBlockQueue = new ArrayBlockingQueue<>(context.count());
    }

    @Override
    public synchronized void acquire() {
        ICurrentTime currentTime = context.currentTime();

        long currentTimeInMills = currentTime.currentTimeInMills();

        //1. 将时间放入队列中 如果放得下，直接可以执行。反之，需要等待
        //2. 等待完成之后，将第一个元素剔除。将最新的时间加入队列中。
        boolean offerResult = timeBlockQueue.offer(currentTimeInMills);
        long intervalInMills = context.timeUnit().toMillis(context.interval());
        if(!offerResult) {
            //获取队列头的元素
            long headTimeInMills = timeBlockQueue.poll();
            //当前时间和头的时间差
            long durationInMills = currentTimeInMills - headTimeInMills;

            if(intervalInMills > durationInMills) {
                //需要沉睡的时间
                long sleepInMills = intervalInMills - durationInMills;
                ILimitHandler limitHandler = context.limitHandler();
                try {
                    limitHandler.beforeHandle();
                    limitHandler.handle(sleepInMills);
                    limitHandler.afterHandle();
                } catch (Exception e) {
                    log.error("GlobalLimitCount.acquire() meet ex: "+e, e);
                }
            }

            currentTimeInMills = currentTime.currentTimeInMills();
            timeBlockQueue.offer(currentTimeInMills);
        }

    }

}
