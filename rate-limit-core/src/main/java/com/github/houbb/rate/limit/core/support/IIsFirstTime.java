/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-acquire All rights reserved.
 */

package com.github.houbb.rate.limit.core.support;

import org.apiguardian.api.API;

/**
 *
 * @author bbhou
 * @since 0.0.1
 */
@API(status = API.Status.INTERNAL)
public interface IIsFirstTime {

    /**
     * 是否为第一次调用
     * (1) 很多时候会使用到第一次调用需要特殊处理。
     * @return {@code true} 是。{@code false} 否
     */
    boolean isFirstTime();


}
