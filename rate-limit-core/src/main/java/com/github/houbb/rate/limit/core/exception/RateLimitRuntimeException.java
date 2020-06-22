/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-acquire All rights reserved.
 */

package com.github.houbb.rate.limit.core.exception;

import org.apiguardian.api.API;

/**
 * <p> 运行时异常 </p>
 *
 * <pre> Created: 2018/8/5 下午10:28  </pre>
 * <pre> Project: rate-acquire  </pre>
 *
 * @author houbinbin
 * @version 0.0.1
 * @since 0.0.1
 */
@API(status = API.Status.INTERNAL)
public class RateLimitRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -7828188205076249256L;

    public RateLimitRuntimeException() {
    }

    public RateLimitRuntimeException(String message) {
        super(message);
    }

    public RateLimitRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public RateLimitRuntimeException(Throwable cause) {
        super(cause);
    }

    public RateLimitRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
