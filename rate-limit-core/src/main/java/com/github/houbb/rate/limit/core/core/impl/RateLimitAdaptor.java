package com.github.houbb.rate.limit.core.core.impl;

import com.github.houbb.rate.limit.core.core.IRateLimit;

/**
 * 适配器
 * @author binbin.hou
 * @since 0.0.5
 */
public class RateLimitAdaptor implements IRateLimit {

    @Override
    public boolean acquire() {
        return false;
    }

    @Override
    public void release() {

    }

}
