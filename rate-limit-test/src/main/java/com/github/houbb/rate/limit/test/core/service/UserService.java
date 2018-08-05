/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-limit All rights reserved.
 */

package com.github.houbb.rate.limit.test.core.service;

import com.github.houbb.rate.limit.spring.annotation.LimitFrequency;

import org.springframework.stereotype.Service;

/**
 * <p> </p>
 *
 * <pre> Created: 2018/8/5 下午10:53  </pre>
 * <pre> Project: rate-limit  </pre>
 *
 * @author houbinbin
 * @version 1.0
 * @since JDK 1.7
 */
@Service
public class UserService {

    @LimitFrequency(interval = 2)
    public void query() {
        System.out.println("query...");
    }

    @LimitFrequency(interval = 2)
    public void query(final long id) {
        System.out.println("query with id...");
    }

}
