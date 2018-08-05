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
 * 限制调用次数
 * Created by bbhou on 2017/9/20.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
@API(status = API.Status.MAINTAINED)
public @interface LimitCount {

    /**
     * 时间单位, 默认为秒
     * @see TimeUnit 时间单位
     * @return 时间单位
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 时间间隔
     * (1) 需要填入正整数。
     * @return 时间间隔
     */
    long interval();

    /**
     * 调用次数。
     * (1) 需要填入正整数。
     * @return 调用次数
     */
    int count();

    /**
     * 限制模式
     * 1. 默认为每个线程都是独立的。
     * @return 限制模式
     */
    LimitModeEnum limitMode() default LimitModeEnum.THREAD_LOCAL;

}
