/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-limit All rights reserved.
 */

package com.github.houbb.rate.limit.core.support.impl;

import org.apiguardian.api.API;

/**
 * 默认的限制处理
 * @author houbinbin
 * @version 1.0
 * @since 1.7
 */
@API(status = API.Status.INTERNAL)
public class DefaultLimitHandler extends AbstractLimitHandler {

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
