/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-tryAcquire All rights reserved.
 */

package com.github.houbb.rate.limit.core.core.impl;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.rate.limit.core.core.IRateLimitContext;
import org.apiguardian.api.API;

/**
 * 令牌桶算法
 *
 * @author houbinbin
 * Created by bbhou on 2017/9/20.
 * @since 0.0.6
 */
@API(status = API.Status.EXPERIMENTAL)
public class RateLimitTokenBucket extends RateLimitAdaptor {

    private static final Log LOG = LogFactory.getLog(RateLimitTokenBucket.class);

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
     * 令牌数量
     *
     * @since 0.0.6
     */
    private volatile long tokenNum;

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
    public RateLimitTokenBucket(final IRateLimitContext context) {
        // 暂不考虑特殊输入，比如 1s 令牌少于1 的场景
        long intervalSeconds = context.timeUnit().toSeconds(context.interval());
        this.rate = context.count() / intervalSeconds;

        // 8 的数据
        this.capacity = this.rate * 8;
        // 这里可以慢慢的加，初始化设置为0
        // 这样就有一个 warmUp 的过程
        this.tokenNum = 0;
        this.lastUpdateTime = System.currentTimeMillis();
    }

    /**
     * 获取锁
     *
     * @since 0.0.5
     */
    @Override
    public synchronized boolean tryAcquire() {

        if (tokenNum < 1) {
            // 加入令牌
            long now = System.currentTimeMillis();
            long durationMs = now - lastUpdateTime;
            long newTokenNum = (long) (durationMs * 1.0 * rate / 1000);

            LOG.debug("[Limit] new token is " + newTokenNum);
            if (newTokenNum > 0) {
                // 超过的部分将舍弃
                this.tokenNum = Math.min(newTokenNum + this.tokenNum, capacity);
                lastUpdateTime = now;
            } else {
                // 时间不够
                return false;
            }
        }

        this.tokenNum--;
        return true;
    }

}
