/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-limit All rights reserved.
 */

package com.github.houbb.rate.limit.core.support;

import org.apiguardian.api.API;

/**
 * 时间差
 *
 * @author bbhou
 * @since 0.0.1
 */
@API(status = API.Status.INTERNAL)
public interface ITimeDiffer {

    /**
     * 获取时间间隔
     * 每调用一次方法，都会保存对应的时间。并会返回本次和上次调用之间的时间差。
     * (1) 单位为毫秒
     * (2) 获取本次调用和上次调用之间的时间间隔。
     * (3) 对于不同的实例化应该是独立的，所以这不应该是个静态类。
     * @return  时间间隔
     */
    long getTimeDiff();


    /**
     * 方法调用完成之后，调用此方法进行更新最后的更新时间。
     */
    void updateAfterCall();

}
