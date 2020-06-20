/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-limit All rights reserved.
 */

package com.github.houbb.rate.limit.spring.annotation;


import com.github.houbb.rate.limit.core.core.ILimit;
import com.github.houbb.rate.limit.core.core.impl.ThreadLocalLimitFrequency;
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
@Target({ElementType.METHOD})
@Inherited
@API(status = API.Status.MAINTAINED)
public @interface Limit {

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
    long interval() default 1;

    /**
     * 调用次数。
     * (1) 需要填入正整数。
     * @return 调用次数
     * @since 0.0.1
     */
    int count() default 100;

    /**
     * 限制策略
     * @return 限制策略
     * @since 0.0.3
     */
    Class<? extends ILimit> limit() default ThreadLocalLimitFrequency.class;

}
