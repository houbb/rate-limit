/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-tryAcquire All rights reserved.
 */

package com.github.houbb.rate.limit.core.annotation;


import org.apiguardian.api.API;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 限制调用
 * Created by bbhou on 2017/9/20.
 * @author binbin.hou
 * @since 0.0.3
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Inherited
@API(status = API.Status.MAINTAINED)
public @interface RateLimit {

    /**
     * 每一次方法请求消耗的令牌数
     * @return 令牌数
     * @since 1.0.0
     */
    int value() default 1;

    /**
     * 时间单位, 默认为秒
     * @see TimeUnit 时间单位
     * @return 时间单位
     * @since 0.0.1
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 时间间隔
     * (1) 需要填入正整数。
     * @return 时间间隔
     * @since 0.0.1
     */
    long interval() default 60;

    /**
     * 调用次数。
     * (1) 需要填入正整数。
     * @return 调用次数
     * @since 0.0.1
     */
    long count() default 1000;

    /**
     * 是否启用
     * @return 结果
     * @since 1.1.0
     */
    boolean enable() default true;

}
