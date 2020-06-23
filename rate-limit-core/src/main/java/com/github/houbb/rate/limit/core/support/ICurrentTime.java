/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-tryAcquire All rights reserved.
 */

package com.github.houbb.rate.limit.core.support;

import org.apiguardian.api.API;

/**
 * 当前时间
 *
 * @author bbhou
 * @since 0.0.1
 */
@API(status = API.Status.INTERNAL)
public interface ICurrentTime {

    /**
     * 当前时间的毫秒数
     * @return  当前时间的毫秒数
     */
    long currentTimeInMills();

}
