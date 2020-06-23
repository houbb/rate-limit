/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-acquire All rights reserved.
 */

package com.github.houbb.rate.limit.core.core.impl;

import com.github.houbb.heaven.support.instance.impl.Instances;
import com.github.houbb.heaven.util.util.DateUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.rate.limit.core.core.IRateLimitContext;
import com.github.houbb.rate.limit.core.support.ICurrentTime;
import com.github.houbb.rate.limit.core.support.impl.CurrentTime;
import org.apiguardian.api.API;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 滑动窗口限制次数
 *
 * 1. 限制 queue 的大小与 count 一致
 * 2. 队首和队尾时间对比
 *
 * 这个和最正宗的滑动窗口有些区别。
 *
 * @author houbinbin
 * Created by bbhou on 2017/9/20.
 * @since 0.0.5
 */
@API(status = API.Status.EXPERIMENTAL)
public class RateLimitSlideWindowQueue extends RateLimitAdaptor {

    private static final Log LOG = LogFactory.getLog(RateLimitSlideWindowQueue.class);

    /**
     * 用于存放时间的队列
     * @since 0.0.3
     */
    private final BlockingQueue<Long> timeBlockQueue;

    /**
     * 当前时间
     * @since 0.0.5
     */
    private final ICurrentTime currentTime = Instances.singleton(CurrentTime.class);

    /**
     * 等待间隔时间
     * @since 0.0.5
     */
    private final long intervalInMills;

    /**
     * 构造器
     * @param context 上下文
     * @since 0.0.3
     */
    public RateLimitSlideWindowQueue(IRateLimitContext context) {
        this.timeBlockQueue = new ArrayBlockingQueue<>(context.count());
        this.intervalInMills = context.timeUnit().toMillis(context.interval());
    }

    @Override
    public synchronized boolean acquire() {
        long currentTimeInMills = currentTime.currentTimeInMills();

        //1. 将时间放入队列中 如果放得下，直接可以执行。反之，需要等待
        //2. 等待完成之后，将第一个元素剔除。将最新的时间加入队列中。
        boolean offerResult = timeBlockQueue.offer(currentTimeInMills);
        if(!offerResult) {
            //获取队列头的元素
            //1. 取出头节点，获取最初的时间
            //2. 将头结点移除
            long headTimeInMills = timeBlockQueue.poll();

            //当前时间和头的时间差
            long durationInMills = currentTimeInMills - headTimeInMills;
            if(intervalInMills > durationInMills) {
                //需要沉睡的时间
                long sleepInMills = intervalInMills - durationInMills;
                DateUtil.sleep(sleepInMills);
            }

            currentTimeInMills = currentTime.currentTimeInMills();
            boolean addResult = timeBlockQueue.offer(currentTimeInMills);
            LOG.debug("[Limit] acquire add result: " + addResult);
        }

        return true;
    }

}
