/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-limit All rights reserved.
 */

package com.github.houbb.rate.limit.core.core;

import org.apiguardian.api.API;

/**
 *
 * @author bbhou
 * @date 2017/9/20
 */
@API(status = API.Status.MAINTAINED)
public interface Limit {

    /**
     * 限制
     */
    void limit() ;

}
