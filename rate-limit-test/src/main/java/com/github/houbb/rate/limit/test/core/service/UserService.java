/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-limit All rights reserved.
 */

package com.github.houbb.rate.limit.test.core.service;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.rate.limit.spring.annotation.LimitCount;
import com.github.houbb.rate.limit.spring.annotation.LimitFrequency;
import com.github.houbb.rate.limit.spring.constant.LimitModeEnum;

import org.springframework.stereotype.Service;

/**
 * <p> </p>
 *
 * <pre> Created: 2018/8/5 下午10:53  </pre>
 * <pre> Project: rate-limit  </pre>
 *
 * @author houbinbin
 * @version 0.0.1
 * @since 0.0.1
 */
@Service
public class UserService {

    private static final Log log = LogFactory.getLog(UserService.class);

    @LimitFrequency(interval = 2)
    public void limitFrequencyThreadLocal() {
        log.info("{}", Thread.currentThread().getName());
    }

    @LimitFrequency(interval = 2, limitMode = LimitModeEnum.GLOBAL)
    public void limitFrequencyGlobal(final long id) {
        log.info("{}", Thread.currentThread().getName());
    }

    @LimitCount(interval = 2, count = 5)
    public void limitCountThreadLocal() {
        log.info("{}", Thread.currentThread().getName());
    }

    @LimitCount(interval = 2, count = 5, limitMode = LimitModeEnum.GLOBAL)
    public void limitCountGlobal() {
        log.info("{}", Thread.currentThread().getName());
    }

}
