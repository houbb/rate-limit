/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-limit All rights reserved.
 */

package com.github.houbb.rate.limit.core.support;

import org.apiguardian.api.API;

/**
 * 如果想对超时的部分直接进行处理。直接实现此类，并设置在 对应的 限制实体类中即可。
 * 1. 下面的方法都是在超时的场景才会调用。
 * @author houbinbin
 * @version 1.0
 * @since 1.7
 */
@API(status = API.Status.INTERNAL)
public interface LimitHandler {

    /**
     * 在限制之前
     * @throws Exception 异常
     */
    void beforeHandle() throws Exception;

    /**
     * 处理
     * 1. 只有当超时时才会调用
     * @param sleepInMills 需要等待的时间
     * @throws Exception 异常
     */
    void handle(long sleepInMills) throws Exception;

    /**
     * 在处理之后
     * @throws Exception 异常
     */
    void afterHandle() throws Exception;

}
