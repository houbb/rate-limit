package com.github.houbb.rate.limit.core.support.method;

import com.github.houbb.heaven.util.lang.reflect.ReflectMethodUtil;
import com.github.houbb.rate.limit.api.support.IRateLimitMethodService;

import java.lang.reflect.Method;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
public class RateLimitMethodService implements IRateLimitMethodService {

    @Override
    public String getMethodId(Method method, Object[] params) {
        // 是否需要包含 class?
        return ReflectMethodUtil.getMethodFullName(method);
    }

}
