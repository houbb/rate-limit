/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-acquire All rights reserved.
 */

package com.github.houbb.rate.limit.test.core.service;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.rate.limit.core.core.impl.LimitSlideWindowQueue;
import com.github.houbb.rate.limit.spring.annotation.Limit;
import org.springframework.stereotype.Service;

/**
 * <p> 服务实现 </p>
 *
 * <pre> Created: 2018/8/5 下午10:53  </pre>
 * <pre> Project: rate-acquire  </pre>
 *
 * @author houbinbin
 * @version 0.0.1
 * @since 0.0.1
 */
@Service
public class UserService {

    private static final Log log = LogFactory.getLog(UserService.class);

    @Limit(interval = 2)
    public void limitFrequencySlide() {
        log.info("{}", Thread.currentThread().getName());
    }

    @Limit(interval = 2, count = 5, limit = LimitSlideWindowQueue.class)
    public void limitCountSlide() {
        log.info("{}", Thread.currentThread().getName());
    }

}
