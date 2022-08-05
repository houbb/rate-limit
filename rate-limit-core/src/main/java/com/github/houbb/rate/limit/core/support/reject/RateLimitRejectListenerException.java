package com.github.houbb.rate.limit.core.support.reject;

import com.github.houbb.rate.limit.api.support.IRateLimitRejectListener;
import com.github.houbb.rate.limit.api.support.IRateLimitRejectListenerContext;
import com.github.houbb.rate.limit.core.exception.RateLimitRuntimeException;

/**
 * 抛出异常
 * @author binbin.hou
 * @since 1.0.0
 */
public class RateLimitRejectListenerException implements IRateLimitRejectListener {

    @Override
    public void listen(IRateLimitRejectListenerContext context) {
        boolean acquireFlag = context.acquireFlag();

        if(!acquireFlag) {
            throw new RateLimitRuntimeException();
        }
    }

}
