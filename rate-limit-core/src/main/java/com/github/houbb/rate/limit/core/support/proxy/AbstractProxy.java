package com.github.houbb.rate.limit.core.support.proxy;

import com.github.houbb.heaven.support.proxy.IProxy;
import com.github.houbb.rate.limit.core.bs.RateLimitBs;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
public abstract class AbstractProxy implements IProxy {

    /**
     * 引导类
     */
    protected final RateLimitBs rateLimitBs;

    protected AbstractProxy(RateLimitBs rateLimitBs) {
        this.rateLimitBs = rateLimitBs;
    }

    public AbstractProxy() {
        this.rateLimitBs = RateLimitBs.newInstance();
    }

}
