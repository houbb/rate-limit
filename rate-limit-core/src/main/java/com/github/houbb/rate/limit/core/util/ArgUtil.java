/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-limit All rights reserved.
 */

package com.github.houbb.rate.limit.core.util;

import org.apiguardian.api.API;

/**
 * 参数工具类
 *
 * @author houbinbin
 * @date 2016/12/29
 */
@API(status = API.Status.INTERNAL)
public final class ArgUtil {

    private ArgUtil() {
    }

    /**
     * 断言不为空
     *
     * @param object 对象
     * @param name   对象名称
     */
    public static void notNull(Object object, String name) {
        if (null == object) {
            throw new IllegalArgumentException(name + " can not be null!");
        }
    }

    /**
     * 判断是否为null
     *
     * @param object 待判断的对象
     * @return {@code true} 为空
     */
    public static Boolean isNull(Object object) {
        return null == object;
    }

    /**
     * 判断是否为非 null
     * @param object 待判断的对象
     * @return {@code true} 为非空
     */
    public static Boolean isNotNull(Object object) {
        return !isNull(object);
    }

}
