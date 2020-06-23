/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-acquire All rights reserved.
 */

package com.github.houbb.rate.limit.core.core.impl;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.rate.limit.core.core.IRateLimitContext;
import org.apiguardian.api.API;

/**
 * 漏桶算法
 *
 * @author houbinbin
 * Created by bbhou on 2017/9/20.
 * @since 0.0.7
 */
@API(status = API.Status.EXPERIMENTAL)
public class RateLimitLeakyBucket extends RateLimitAdaptor {

    private static final Log LOG = LogFactory.getLog(RateLimitLeakyBucket.class);

    /**
     * 令牌的发放速率
     * <p>
     * 每一秒发放多少。
     *
     * @since 0.0.6
     */
    private final long rate;

    /**
     * 容量
     * <p>
     * 后期暴露为可以配置
     *
     * @since 0.0.6
     */
    private final long capacity;

    /**
     * 水量
     *
     * @since 0.0.6
     */
    private volatile long water;

    /**
     * 上一次的更新时间
     *
     * @since 0.0.6
     */
    private volatile long lastUpdateTime;

    /**
     * 构造器
     *
     * @param context 上下文
     * @since 0.0.4
     */
    public RateLimitLeakyBucket(final IRateLimitContext context) {
        // 暂不考虑特殊输入，比如 1s 令牌少于1 的场景
        long intervalSeconds = context.timeUnit().toSeconds(context.interval());
        this.rate = context.count() / intervalSeconds;

        // 8 的数据
        this.capacity = this.rate * 8;
        // 这里可以慢慢的加，初始化设置为0
        // 这样就有一个 warmUp 的过程
        this.water = 0;
        this.lastUpdateTime = System.currentTimeMillis();
    }

    /**
     * 获取锁
     *
     * （1）未满加水：通过代码 water +=1进行不停加水的动作。
     * （2）漏水：通过时间差来计算漏水量。
     * （3）剩余水量：总水量-漏水量。
     *
     * @since 0.0.5
     */
    @Override
    public synchronized boolean acquire() {
        long now = System.currentTimeMillis();
        // 先执行漏水，计算剩余水量
        long durationMs = now - lastUpdateTime;
        long leakyWater = (long) (durationMs * 1.0 * rate / 1000);
        LOG.debug("[Limit] leaky water is " + leakyWater);
        water = Math.max(0, water - leakyWater);


        // 这里应该加一个判断，如果漏水量较小，直接返回。避免开始时，大量流量通过
        if(leakyWater < 1) {
            LOG.debug("[Limit] leaky water is too small!");
            return false;
        }

        if ((water + 1) < capacity) {
            // 尝试加水,并且水还未满
            water++;

            lastUpdateTime = now;
            return true;
        } else {
            // 水满，拒绝加水
            LOG.debug("[Limit] leaky water is has been full!");
            return false;
        }
    }

}
