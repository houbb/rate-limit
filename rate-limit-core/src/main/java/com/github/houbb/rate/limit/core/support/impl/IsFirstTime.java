/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-limit All rights reserved.
 */

package com.github.houbb.rate.limit.core.support.impl;


import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.rate.limit.core.support.IIsFirstTime;

import org.apiguardian.api.API;

/**
 *
 * @author bbhou
 * @since 0.0.1
 */
@API(status = API.Status.INTERNAL)
@ThreadSafe
public class IsFirstTime implements IIsFirstTime {

    /**
     * 是否为第一次调用-标志位
     */
    private volatile boolean flag = true;

    @Override
    public boolean isFirstTime() {
        boolean result = flag;
        if(flag) {
            flag = false;
        }
        return result;
    }

}
