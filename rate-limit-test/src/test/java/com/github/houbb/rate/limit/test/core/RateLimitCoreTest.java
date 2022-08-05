/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-tryAcquire All rights reserved.
 */

package com.github.houbb.rate.limit.test.core;


import com.github.houbb.rate.limit.core.exception.RateLimitRuntimeException;
import com.github.houbb.rate.limit.core.support.proxy.RateLimitProxy;
import com.github.houbb.rate.limit.test.core.service.UserService;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * <p> </p>
 *
 * <pre> Created: 2018/8/1 上午9:53  </pre>
 * <pre> Project: spring-framework-learn  </pre>
 *
 * @author houbinbin
 * @version 0.0.1
 * @since 0.0.1
 */
public class RateLimitCoreTest {

    @Test
    public void limitCountTest() throws InterruptedException {
        UserService userService = RateLimitProxy.getProxy(new UserService());

        for(int i = 0; i < 4; i++) {
            TimeUnit.SECONDS.sleep(1);
            userService.limitCount();
        }
    }

    @Test(expected = RateLimitRuntimeException.class)
    public void limitCountErrorTest() {
        UserService userService = RateLimitProxy.getProxy(new UserService());
        for(int i = 0; i < 3; i++) {
            userService.limitCount();
        }
    }

}
