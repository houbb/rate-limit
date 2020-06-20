/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-limit All rights reserved.
 */

package com.github.houbb.rate.limit.core.support.impl;


import com.github.houbb.rate.limit.core.support.IsFirstTime;

import org.apiguardian.api.API;

/**
 *
 * @author bbhou
 * @since 0.0.1
 */
@API(status = API.Status.INTERNAL)
public class SimpleIsFirstTime implements IsFirstTime {

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
