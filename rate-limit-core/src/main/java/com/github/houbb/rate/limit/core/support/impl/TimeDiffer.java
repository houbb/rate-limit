/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-acquire All rights reserved.
 */

package com.github.houbb.rate.limit.core.support.impl;


import com.github.houbb.heaven.annotation.NotThreadSafe;
import com.github.houbb.rate.limit.core.support.ITimeDiffer;

import org.apiguardian.api.API;

/**
 * 简单的时间差异实现
 *
 * @author bbhou
 * @since 0.0.1
 */
@API(status = API.Status.INTERNAL)
public class TimeDiffer implements ITimeDiffer {

    /**
     * 记录上一次时间
     */
    private volatile long previousInMills = 0L;

    @Override
    public long getTimeDiff() {
        //时间差值
        return System.currentTimeMillis() - previousInMills;
    }

    @Override
    public void updateAfterCall() {
        //设置为当前时间
        previousInMills = System.currentTimeMillis();
    }

}
