/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-tryAcquire All rights reserved.
 */

package com.github.houbb.rate.limit.core.core;

import com.github.houbb.common.cache.api.service.ICommonCacheService;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.rate.limit.api.core.IRateLimitContext;
import com.github.houbb.rate.limit.api.dto.RateLimitConfigDto;
import org.apiguardian.api.API;

/**
 * 固定时间窗口
 *
 * @author houbinbin
 * Created by bbhou on 2017/9/20.
 * @since 0.0.5
 */
@API(status = API.Status.EXPERIMENTAL)
public class RateLimitFixedWindow extends AbstractRateLimit {

    /**
     * 日志
     * @since 0.0.5
     */
    private static final Log LOG = LogFactory.getLog(RateLimitFixedWindow.class);

    /**
     * 固定窗口的实现方式比较简单，直接设置过期时间即可。
     *
     * @param cacheKey 缓存标识
     * @param configDto 配置
     * @param rateLimitContext 上下文
     * @return 结果
     */
    @Override
    protected boolean doAcquire(String cacheKey, RateLimitConfigDto configDto, IRateLimitContext rateLimitContext) {
        final ICommonCacheService cacheService = rateLimitContext.cacheService();
        final int permits = configDto.getPermits();

        String cacheValue = cacheService.get(cacheKey);
        if(StringUtil.isEmpty(cacheKey)) {
            final long expireMills = configDto.getTimeUnit().toMillis(configDto.getInterval());
            LOG.info("cacheKey: {} 对应的历史配置为空，进行初始化");
            // 模式初始化为0次
            cacheValue = "0";
            cacheService.set(cacheKey, cacheValue, expireMills);
        }

        long cacheCount = Long.parseLong(cacheValue);

        long newCount = cacheCount + permits;
        final long configCount = configDto.getCount();
        if(newCount > configCount) {
            LOG.warn("newCount {} 大于配置的 {}", newCount, configCount);
            return false;
        } else {
            long ttlMills = cacheService.ttl(cacheKey);
            if(ttlMills > 0) {
                // 直接 set 一个值，redis 会将其有效期设置为永远。
                cacheService.set(cacheKey, cacheValue, ttlMills);
            }

            return true;
        }
    }

}
