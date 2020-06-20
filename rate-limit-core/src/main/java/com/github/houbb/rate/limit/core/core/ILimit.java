/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-limit All rights reserved.
 */

package com.github.houbb.rate.limit.core.core;

import org.apiguardian.api.API;

/**
 *
 * 限流核心接口
 * @author bbhou
 * @since 0.0.1
 * @since 0.0.1
 */
@API(status = API.Status.MAINTAINED)
public interface ILimit {

    /**
     * 限制
     * @since 0.0.1
     */
    void limit() ;

}
