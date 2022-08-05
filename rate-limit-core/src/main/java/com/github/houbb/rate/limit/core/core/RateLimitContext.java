package com.github.houbb.rate.limit.core.core;

import com.github.houbb.common.cache.api.service.ICommonCacheService;
import com.github.houbb.rate.limit.api.core.IRateLimitContext;
import com.github.houbb.rate.limit.api.support.IRateLimitConfigService;
import com.github.houbb.rate.limit.api.support.IRateLimitMethodService;
import com.github.houbb.rate.limit.api.support.IRateLimitRejectListener;
import com.github.houbb.rate.limit.api.support.IRateLimitTokenService;
import com.github.houbb.timer.api.ITimer;

import java.lang.reflect.Method;

/**
 * <p> project: rate-tryAcquire-RateLimitContext </p>
 * <p> create on 2020/6/20 21:35 </p>
 *
 * @author binbin.hou
 * @since 0.0.3
 */
public class RateLimitContext implements IRateLimitContext {

    /**
     * 时间戳
     */
    private ITimer timer;

    /**
     * 配置服务类
     * @since 1.0.0
     */
    private IRateLimitConfigService configService;

    /**
     * 方法服务类
     * @since 1.0.0
     */
    private IRateLimitMethodService methodService;

    /**
     * 标识服务
     * @since 1.0.0
     */
    private IRateLimitTokenService tokenService;

    /**
     * 缓存服务
     */
    private ICommonCacheService cacheService;

    /**
     * 拒绝时的监听策略
     * @since 1.0.0
     */
    private IRateLimitRejectListener rejectListener;

    /**
     * 访问的方法
     */
    private Method method;

    /**
     * 访问的方法参数
     */
    private Object[] args;

    public static RateLimitContext newInstance() {
        return new RateLimitContext();
    }

    @Override
    public ITimer timer() {
        return timer;
    }

    public RateLimitContext timer(ITimer timer) {
        this.timer = timer;
        return this;
    }

    @Override
    public IRateLimitConfigService configService() {
        return configService;
    }

    public RateLimitContext configService(IRateLimitConfigService configService) {
        this.configService = configService;
        return this;
    }

    @Override
    public IRateLimitMethodService methodService() {
        return methodService;
    }

    public RateLimitContext methodService(IRateLimitMethodService methodService) {
        this.methodService = methodService;
        return this;
    }

    @Override
    public IRateLimitTokenService tokenService() {
        return tokenService;
    }

    public RateLimitContext tokenService(IRateLimitTokenService tokenService) {
        this.tokenService = tokenService;
        return this;
    }


    @Override
    public IRateLimitRejectListener rejectListener() {
        return rejectListener;
    }

    public RateLimitContext rejectListener(IRateLimitRejectListener rejectListener) {
        this.rejectListener = rejectListener;
        return this;
    }

    @Override
    public Method method() {
        return method;
    }

    public RateLimitContext method(Method method) {
        this.method = method;
        return this;
    }

    @Override
    public Object[] args() {
        return args;
    }

    public RateLimitContext args(Object[] args) {
        this.args = args;
        return this;
    }

    @Override
    public ICommonCacheService cacheService() {
        return cacheService;
    }

    public RateLimitContext cacheService(ICommonCacheService cacheService) {
        this.cacheService = cacheService;
        return this;
    }
}
