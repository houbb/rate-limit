/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-tryAcquire All rights reserved.
 */

package com.github.houbb.rate.limit.test.core.service;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.rate.limit.core.annotation.RateLimit;
import com.github.houbb.rate.limit.core.core.RateLimitSlideWindowQueue;
import org.springframework.stereotype.Service;

/**
 * <p> 服务实现 </p>
 *
 * <pre> Created: 2018/8/5 下午10:53  </pre>
 * <pre> Project: rate-tryAcquire  </pre>
 *
 * @author houbinbin
 * @version 0.0.1
 * @since 0.0.1
 */
@Service
public class UserService {

    private static final Log log = LogFactory.getLog(UserService.class);

    @RateLimit(interval = 2, count = 5)
    public void limitCount() {
        log.info("{}", Thread.currentThread().getName());
    }

}
