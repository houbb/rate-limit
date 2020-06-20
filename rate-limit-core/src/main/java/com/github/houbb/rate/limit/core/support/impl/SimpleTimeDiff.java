/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-limit All rights reserved.
 */

package com.github.houbb.rate.limit.core.support.impl;


import com.github.houbb.rate.limit.core.support.TimeDiff;

import org.apiguardian.api.API;

/**
 * 简单的时间差异实现
 *
 * @author bbhou
 * @since 0.0.1
 */
@API(status = API.Status.INTERNAL)
public class SimpleTimeDiff implements TimeDiff {

    /**
     * 记录上一次时间
     */
    private long previousInMills = 0L;

    @Override
    public long getTimeDiff() {
        //当前时间
        long currentInMills = System.currentTimeMillis();
        //时间差值
        return currentInMills-previousInMills;
    }

    @Override
    public void updateAfterCall() {
        //设置为当前时间
        previousInMills = System.currentTimeMillis();
    }

}
