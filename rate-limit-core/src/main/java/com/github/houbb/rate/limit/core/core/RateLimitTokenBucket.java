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
import com.github.houbb.rate.limit.core.dto.RateLimitTokenBucketDto;
import com.github.houbb.rate.limit.core.util.InnerRateLimitUtils;
import com.github.houbb.timer.api.ITimer;
import org.apiguardian.api.API;

/**
 * 令牌桶算法
 *
 * @author houbinbin
 * Created by bbhou on 2017/9/20.
 * @since 0.0.6
 */
@API(status = API.Status.EXPERIMENTAL)
public class RateLimitTokenBucket extends AbstractRateLimit {

    private static final Log LOG = LogFactory.getLog(RateLimitTokenBucket.class);

    /**
     * 尝试获取锁
     *
     * @param cacheKey  缓存标识
     * @param configDto 配置对象
     * @param context   上下文
     * @return 结果
     */
    @Override
    public boolean doAcquire(String cacheKey,
                             RateLimitConfigDto configDto,
                             IRateLimitContext context) {
        final long rate = InnerRateLimitUtils.calcRate(configDto);
        RateLimitTokenBucketDto rateLimitTokenBucketDto = getRateLimitBucketDto(cacheKey, rate, context);
        final ICommonCacheService commonCacheService = context.cacheService();
        final ITimer timer = context.timer();

        int permits = configDto.getPermits();
        long tokenNum = rateLimitTokenBucketDto.getTokenNum();
        if (tokenNum < permits) {
            // 加入令牌
            long now = timer.time();
            long durationMs = now - rateLimitTokenBucketDto.getLastUpdateTime();
            // 增量部分
            long addTokenNum = (long) (durationMs * 1.0 * rate / 1000);
            LOG.debug("[Limit] add token is " + addTokenNum);

            // 新的令牌数量，丢弃超出的部分
            long newTokenNum = Math.min(addTokenNum + tokenNum, rateLimitTokenBucketDto.getCapacity());
            if (newTokenNum >= permits) {
                rateLimitTokenBucketDto.setLastUpdateTime(now);
                rateLimitTokenBucketDto.setTokenNum(newTokenNum - permits);
                commonCacheService.set(cacheKey, JSON.toJSONString(rateLimitTokenBucketDto));
                return true;
            } else {
                // 时间不够
                return false;
            }
        } else {
            // 正常够使用的场景
            rateLimitTokenBucketDto.setTokenNum(tokenNum - permits);
            commonCacheService.set(cacheKey, JSON.toJSONString(rateLimitTokenBucketDto));
        }

        return true;
    }


    /**
     * 获取对应的配置信息
     *
     * @param cacheKey 缓存 key
     * @param rate     速率
     * @param context  上下文
     * @return 结果
     */
    private RateLimitTokenBucketDto getRateLimitBucketDto(String cacheKey,
                                                          long rate,
                                                          IRateLimitContext context) {
        final ICommonCacheService commonCacheService = context.cacheService();
        final ITimer timer = context.timer();

        String dtoJson = commonCacheService.get(cacheKey);
        RateLimitTokenBucketDto bucketDto = null;
        if (StringUtil.isNotEmpty(dtoJson)) {
            bucketDto = JSON.parseObject(dtoJson, RateLimitTokenBucketDto.class);
        } else {
            // 初始化
            bucketDto = new RateLimitTokenBucketDto();
            bucketDto.setRate(rate);
            bucketDto.setCapacity(rate * 8);
            // 默认初始化为 1，应该比较合理一点
            // 如果是0，可能导致一开始无法访问
            bucketDto.setTokenNum(1);
            bucketDto.setLastUpdateTime(timer.time());
        }

        return bucketDto;
    }

}
