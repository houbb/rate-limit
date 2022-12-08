package com.github.houbb.rate.limit.test.bs;

import com.github.houbb.common.cache.core.service.CommonCacheServiceMap;
import com.github.houbb.rate.limit.core.annotation.RateLimit;
import com.github.houbb.rate.limit.core.bs.RateLimitBs;
import com.github.houbb.rate.limit.core.constant.RateLimitConst;
import com.github.houbb.rate.limit.core.core.RateLimits;
import com.github.houbb.rate.limit.core.support.config.RateLimitConfigService;
import com.github.houbb.rate.limit.core.support.method.RateLimitMethodService;
import com.github.houbb.rate.limit.core.support.reject.RateLimitRejectListenerException;
import com.github.houbb.rate.limit.core.support.token.RateLimitTokenService;
import com.github.houbb.timer.core.util.Timers;
import org.junit.Test;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
public class RateLimitBsTest {

    @Test
    public void defaultTest() {
        RateLimitBs.newInstance()
                .timer(Timers.system())
                .methodService(new RateLimitMethodService())
                .tokenService(new RateLimitTokenService())
                .rejectListener(new RateLimitRejectListenerException())
                .configService(new RateLimitConfigService())
                .cacheService(new CommonCacheServiceMap())
                .rateLimit(RateLimits.tokenBucket())
                .cacheKeyNamespace(RateLimitConst.DEFAULT_CACHE_KEY_NAMESPACE);
    }
}
