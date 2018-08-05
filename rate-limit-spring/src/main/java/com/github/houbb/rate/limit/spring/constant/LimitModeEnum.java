/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-limit All rights reserved.
 */

package com.github.houbb.rate.limit.spring.constant;

import org.apiguardian.api.API;

/**
 * 限制模式
 *
 * @author bbhou
 * @date 2017/9/22
 */
@API(status = API.Status.MAINTAINED)
public enum LimitModeEnum {

    /**
     * 对于每一个线程提供独立的限制
     */
    THREAD_LOCAL("thread_local"),

    /**
     * 全局模式
     */
    GLOBAL("global");

    /**
     * 类型
     */
    private String mode;

    LimitModeEnum(String mode) {
        this.mode = mode;
    }

    /**
     * 获取对应模式
     * @return  对应模式
     */
    public String getMode() {
        return mode;
    }


    /**
     * 是否为全局模式
     * @param limitModeEnum 限制模式
     * @return {@code true} 是全局
     */
    public static boolean isGlobal(LimitModeEnum limitModeEnum) {
        return GLOBAL.equals(limitModeEnum);
    }

    /**
     * 是否为 线程独立
     * @param limitModeEnum 限制模式
     * @return {@code true} 是局部
     */
    public static boolean isThreadLocal(LimitModeEnum limitModeEnum) {
        return THREAD_LOCAL.equals(limitModeEnum);
    }

}
