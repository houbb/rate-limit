/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-acquire All rights reserved.
 */

package com.github.houbb.rate.limit.core.support.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import org.apiguardian.api.API;

/**
 * 默认的限制处理
 * @author houbinbin
 * @version 0.0.1
 * @since 0.0.1
 */
@API(status = API.Status.INTERNAL)
@ThreadSafe
public class LimitHandler extends LimitHandlerAdaptor {

    /**
     * 默认沉睡
     * @param sleepInMills 需要等待的时间
     * @throws Exception 异常信息
     */
    @Override
    public void handle(long sleepInMills) throws Exception {
        Thread.sleep(sleepInMills);
    }

}
