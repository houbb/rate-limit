/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-limit All rights reserved.
 */

package com.github.houbb.rate.limit.core.support;

import org.apiguardian.api.API;

/**
 *
 * @author bbhou
 * @date 2017/9/21
 */
@API(status = API.Status.INTERNAL)
public interface IsFirstTime {

    /**
     * 是否为第一次调用
     * (1) 很多时候会使用到第一次调用需要特殊处理。
     * @return {@code true} 是。{@code false} 否
     */
    boolean isFirstTime();


}
