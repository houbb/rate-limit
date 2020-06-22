package com.github.houbb.rate.limit.core.core.impl;

import com.github.houbb.rate.limit.core.core.ILimit;

/**
 * 适配器
 * @author binbin.hou
 * @since 0.0.5
 */
public class LimitAdaptor implements ILimit {

    @Override
    public boolean acquire() {
        return false;
    }

    @Override
    public void release() {

    }

}
