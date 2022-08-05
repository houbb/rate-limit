/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-tryAcquire All rights reserved.
 */

package com.github.houbb.rate.limit.core.core;

import com.alibaba.fastjson.JSON;
import com.github.houbb.common.cache.api.service.ICommonCacheService;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.rate.limit.api.core.IRateLimitContext;
import com.github.houbb.rate.limit.api.dto.RateLimitConfigDto;
import com.github.houbb.timer.api.ITimer;
import org.apiguardian.api.API;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * 滑动窗口限制次数
 * <p>
 * 1. 限制 queue 的大小与 count 一致
 * 2. 队首和队尾时间对比
 * <p>
 * 这个和最正宗的滑动窗口有些区别。
 *
 * @author houbinbin
 * Created by bbhou on 2017/9/20.
 * @since 0.0.5
 */
@API(status = API.Status.EXPERIMENTAL)
public class RateLimitSlideWindowQueue extends AbstractRateLimit {

    private static final Log LOG = LogFactory.getLog(RateLimitSlideWindowQueue.class);

    @Override
    protected boolean doAcquire(String cacheKey, RateLimitConfigDto configDto, IRateLimitContext context) {
        // 队列？
        Queue<Long> queue = queryQueue(cacheKey, configDto, context);

        //1. 将时间放入队列中 如果放得下，直接可以执行。反之，需要等待
        //2. 等待完成之后，将第一个元素剔除。将最新的时间加入队列中。
        final ICommonCacheService cacheService = context.cacheService();
        final ITimer timer = context.timer();
        long now = timer.time();

        //2.1 直接放入成功
        boolean offerResult = queue.offer(now);
        if (offerResult) {
            String cacheValue = JSON.toJSONString(queue);
            cacheService.set(cacheKey, cacheValue);
            return true;
        }

        //2.2 直接放入失败
        //2.2.1 取出头节点，获取最初的时间
        long intervalInMills = configDto.getTimeUnit().toMillis(configDto.getInterval());
        Long headTimeInMills = queue.peek();
        long durationMills = now - headTimeInMills;
        if (durationMills > intervalInMills) {
            Long headTimeRemove = queue.poll();
            queue.offer(now);

            LOG.info("Remove head value: {}, add new value: {}",
                    headTimeRemove, now);

            String cacheValue = JSON.toJSONString(queue);
            cacheService.set(cacheKey, cacheValue);
            return true;
        }

        return false;
    }

    private Queue<Long> queryQueue(String cacheKey,
                                   RateLimitConfigDto configDto,
                                   IRateLimitContext context) {
        final ICommonCacheService cacheService = context.cacheService();
        String cacheValue = cacheService.get(cacheKey);

        int count = configDto.getCount().intValue();
        Queue<Long> queue = new ArrayBlockingQueue<>(count);

        if (StringUtil.isNotEmpty(cacheValue)) {
            List<Long> list = JSON.parseArray(cacheValue, Long.class);
            queue.addAll(list);
            return queue;
        } else {
            return queue;
        }
    }

}
