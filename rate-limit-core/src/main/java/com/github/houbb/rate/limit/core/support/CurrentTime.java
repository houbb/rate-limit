/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-limit All rights reserved.
 */

package com.github.houbb.rate.limit.core.support;

import org.apiguardian.api.API;

/**
 * 当前时间
 *
 * @author bbhou
 * @date 2017/9/21
 */
@API(status = API.Status.INTERNAL)
public interface CurrentTime {

    /**
     * 当前时间的毫秒数
     * @return  当前时间的毫秒数
     */
    long currentTimeInMills();

}
