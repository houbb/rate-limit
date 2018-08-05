/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-limit All rights reserved.
 */

package com.github.houbb.rate.limit.spring.annotation;


import com.github.houbb.rate.limit.spring.constant.LimitModeEnum;

import org.apiguardian.api.API;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 限制调用频率
 * Created by bbhou on 2017/9/20.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
@API(status = API.Status.MAINTAINED)
public @interface LimitFrequency {

    /**
     * 时间单位, 默认为秒
     * @return 时间单位
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 时间间隔, 默认为无。
     * (1) 时间间隔应该为正整数。其他一律视为无时间间隔。
     * @return 时间间隔
     */
    long interval();

    /**
     * 限制模式
     * 1. 默认为每个线程都是独立的。
     * @see LimitModeEnum 限制模式枚举
     * @return 限制模式
     */
    LimitModeEnum limitMode() default LimitModeEnum.THREAD_LOCAL;

}
