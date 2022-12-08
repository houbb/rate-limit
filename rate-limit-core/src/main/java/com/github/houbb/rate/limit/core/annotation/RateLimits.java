/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-tryAcquire All rights reserved.
 */

package com.github.houbb.rate.limit.core.annotation;


import org.apiguardian.api.API;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 限制调用，支持定义多个。
 *
 * @author binbin.hou
 * @since 1.1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Inherited
@API(status = API.Status.MAINTAINED)
public @interface RateLimits {

    /**
     * 对应的数组列表
     * @return 结果
     */
    RateLimit[] value();

}
