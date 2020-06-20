/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-limit All rights reserved.
 */

package com.github.houbb.rate.limit.core.support.impl;


import com.github.houbb.rate.limit.core.support.ILimitHandler;

import org.apiguardian.api.API;

/**
 * 抽象的限制处理
 * 1. 用户自定义时可以覆写这个类的方法。本类所有实现暂时为空。
 * 2. 如果想超时直接使用默认方法 {@link LimitHandler} 继承这个方法是不错的选择。
 * @author houbinbin
 * @version 0.0.1
 * @since 0.0.1
 */
@API(status = API.Status.INTERNAL)
public class LimitHandlerAdaptor implements ILimitHandler {

    @Override
    public void beforeHandle() throws Exception {

    }

    @Override
    public void handle(long sleepInMills) throws Exception {

    }

    @Override
    public void afterHandle() throws Exception {

    }

}
