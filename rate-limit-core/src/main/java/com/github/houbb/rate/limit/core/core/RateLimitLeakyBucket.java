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
import com.github.houbb.rate.limit.core.dto.RateLimitLeakyBucketDto;
import com.github.houbb.rate.limit.core.util.InnerRateLimitUtils;
import com.github.houbb.timer.api.ITimer;
import org.apiguardian.api.API;

/**
 * 漏桶算法
 *
 * @author houbinbin
 * Created by bbhou on 2017/9/20.
 * @since 0.0.7
 */
@API(status = API.Status.EXPERIMENTAL)
public class RateLimitLeakyBucket extends AbstractRateLimit {

    private static final Log LOG = LogFactory.getLog(RateLimitLeakyBucket.class);

    /**
     * 尝试获取锁
     * @param cacheKey 缓存标识
     * @param configDto 配置对象
     * @param context 上下文
     * @return 结果
     */
    @Override
    public boolean doAcquire(String cacheKey,
                                          RateLimitConfigDto configDto,
                                          IRateLimitContext context) {
        final long rate = InnerRateLimitUtils.calcRate(configDto);
        RateLimitLeakyBucketDto rateLimitTokenBucketDto = getRateLimitBucketDto(cacheKey, rate, context);

        int permits = configDto.getPermits();
        long water = calcWater(rateLimitTokenBucketDto, context, rate);
        final long capacity = rateLimitTokenBucketDto.getCapacity();
        final long newWater = water + permits;
        if (newWater <= capacity) {
            final ITimer timer = context.timer();

            // 尝试加水,并且水还未满
            rateLimitTokenBucketDto.setWater(newWater);
            rateLimitTokenBucketDto.setLastUpdateTime(timer.time());

            final ICommonCacheService commonCacheService = context.cacheService();
            commonCacheService.set(cacheKey, JSON.toJSONString(rateLimitTokenBucketDto));
            return true;
        } else {
            // 水满，拒绝加水
            LOG.info("[RateLimit] leaky water is has been full!");
            return false;
        }
    }

    /**
     * 首先计算一次数量
     * @param bucketDto 信息
     * @param rateLimitContext 上下文
     * @param rate 速率
     * @return 结果
     */
    private long calcWater(RateLimitLeakyBucketDto bucketDto,
                           IRateLimitContext rateLimitContext,
                           final long rate) {
        long now = rateLimitContext.timer().time();
        long lastUpdateTime = bucketDto.getLastUpdateTime();
        // 先执行漏水，计算剩余水量
        long durationMs = now - lastUpdateTime;
        long leakyWater = (long) (durationMs * 1.0 * rate / 1000);
        LOG.info("[RateLimit] leaky water is " + leakyWater);
        long water = bucketDto.getWater();

        // 确保最小为 0
        return Math.max(0, water - leakyWater);
    }

    /**
     * 获取对应的配置信息
     * @param cacheKey 缓存 key
     * @param rate 速率
     * @param context 上下文
     * @return 结果
     */
    private RateLimitLeakyBucketDto getRateLimitBucketDto(String cacheKey,
                                                          long rate,
                                                          IRateLimitContext context) {
        final ICommonCacheService commonCacheService = context.cacheService();
        final ITimer timer = context.timer();

        String dtoJson = commonCacheService.get(cacheKey);
        RateLimitLeakyBucketDto bucketDto = null;
        if(StringUtil.isNotEmpty(dtoJson)) {
            bucketDto = JSON.parseObject(dtoJson, RateLimitLeakyBucketDto.class);
        } else {
            // 初始化
            bucketDto = new RateLimitLeakyBucketDto();
            bucketDto.setRate(rate);
            bucketDto.setCapacity(rate * 8);

            // 水量初始化为 0
            bucketDto.setWater(0);
            bucketDto.setLastUpdateTime(timer.time());
        }

        return bucketDto;
    }


}
