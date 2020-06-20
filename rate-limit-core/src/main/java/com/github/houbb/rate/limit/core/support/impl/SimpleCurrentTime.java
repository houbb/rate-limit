/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-limit All rights reserved.
 */

package com.github.houbb.rate.limit.core.support.impl;


import com.github.houbb.rate.limit.core.support.CurrentTime;

import org.apiguardian.api.API;

/**
 *
 * @author bbhou
 * @since 0.0.1
 */
@API(status = API.Status.INTERNAL)
public class SimpleCurrentTime implements CurrentTime {

    @Override
    public long currentTimeInMills() {
        return System.currentTimeMillis();
    }

}
