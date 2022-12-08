package com.github.houbb.rate.limit.core.bs;

import com.github.houbb.common.cache.api.service.ICommonCacheService;
import com.github.houbb.common.cache.core.service.CommonCacheServiceMap;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.rate.limit.api.core.IRateLimit;
import com.github.houbb.rate.limit.api.core.IRateLimitContext;
import com.github.houbb.rate.limit.api.support.IRateLimitConfigService;
import com.github.houbb.rate.limit.api.support.IRateLimitMethodService;
import com.github.houbb.rate.limit.api.support.IRateLimitRejectListener;
import com.github.houbb.rate.limit.api.support.IRateLimitTokenService;
import com.github.houbb.rate.limit.core.constant.RateLimitConst;
import com.github.houbb.rate.limit.core.core.RateLimitContext;
import com.github.houbb.rate.limit.core.core.RateLimits;
import com.github.houbb.rate.limit.core.support.config.RateLimitConfigService;
import com.github.houbb.rate.limit.core.support.method.RateLimitMethodService;
import com.github.houbb.rate.limit.core.support.reject.RateLimitRejectListenerException;
import com.github.houbb.rate.limit.core.support.token.RateLimitTokenService;
import com.github.houbb.timer.api.ITimer;
import com.github.houbb.timer.core.util.Timers;

import java.lang.reflect.Method;

/**
 * <p> project: rate-tryAcquire-RateLimitBs </p>
 * <p> create on 2020/6/20 21:38 </p>
 *
 * @author binbin.hou
 * @since 0.0.3
 */
public final class RateLimitBs {

    private RateLimitBs(){}

    /**
     * 新建对象实例
     * @return this
     * @since 0.0.1
     */
    public static RateLimitBs newInstance() {
        return new RateLimitBs();
    }

    /**
     * 限流算法
     */
    private IRateLimit rateLimit = RateLimits.tokenBucket();

    /**
     * 时间策略
     * @since 1.0.0
     */
    private ITimer timer = Timers.system();

    /**
     * 缓存策略
     * @since 1.0.0
     */
    private ICommonCacheService cacheService = new CommonCacheServiceMap();

    /**
     * 配置服务
     * @since 1.0.0
     */
    private IRateLimitConfigService configService = new RateLimitConfigService();

    /**
     * 标识服务类
     * @since 1.0.0
     */
    private IRateLimitTokenService tokenService = new RateLimitTokenService();

    /**
     * 方法标识策略
     * @since 1.0.0
     */
    private IRateLimitMethodService methodService = new RateLimitMethodService();

    /**
     * 拒绝策略
     * @since 1.0.0
     */
    private IRateLimitRejectListener rejectListener = new RateLimitRejectListenerException();

    /**
     * 对应的缓存 key 命名空间
     * @since 1.1.0
     */
    private String cacheKeyNamespace = RateLimitConst.DEFAULT_CACHE_KEY_NAMESPACE;

    public RateLimitBs rateLimit(IRateLimit rateLimit) {
        ArgUtil.notNull(rateLimit, "rateLimit");

        this.rateLimit = rateLimit;
        return this;
    }

    public RateLimitBs timer(ITimer timer) {
        ArgUtil.notNull(timer, "timer");

        this.timer = timer;
        return this;
    }

    public RateLimitBs cacheService(ICommonCacheService cacheService) {
        ArgUtil.notNull(cacheService, "cacheService");

        this.cacheService = cacheService;
        return this;
    }

    public RateLimitBs configService(IRateLimitConfigService configService) {
        ArgUtil.notNull(configService, "configService");

        this.configService = configService;
        return this;
    }

    public RateLimitBs tokenService(IRateLimitTokenService tokenService) {
        ArgUtil.notNull(tokenService, "tokenService");

        this.tokenService = tokenService;
        return this;
    }

    public RateLimitBs methodService(IRateLimitMethodService methodService) {
        ArgUtil.notNull(methodService, "methodService");

        this.methodService = methodService;
        return this;
    }

    public RateLimitBs rejectListener(IRateLimitRejectListener rejectListener) {
        ArgUtil.notNull(rejectListener, "rejectListener");

        this.rejectListener = rejectListener;
        return this;
    }

    public RateLimitBs cacheKeyNamespace(String cacheKeyNamespace) {
        ArgUtil.notEmpty(cacheKeyNamespace, "cacheKeyNamespace");

        this.cacheKeyNamespace = cacheKeyNamespace;
        return this;
    }

    /**
     * 尝试获取锁
     * @param method 方法
     * @param args 入参
     * @return 结果
     * @since 1.0.0
     */
    public boolean tryAcquire(Method method,
                              Object[] args) {
        ArgUtil.notNull(method, "method");

        IRateLimitContext rateLimitContext = RateLimitContext.newInstance()
                .method(method)
                .args(args)
                .timer(timer)
                .configService(configService)
                .tokenService(tokenService)
                .methodService(methodService)
                .rejectListener(rejectListener)
                .cacheService(cacheService)
                .cacheKeyNamespace(cacheKeyNamespace);

        return rateLimit.tryAcquire(rateLimitContext);
    }

}
