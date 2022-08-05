package com.github.houbb.rate.limit.api.support;

import java.lang.reflect.Method;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
public interface IRateLimitMethodService {

    /**
     * 根据入参构建对应的 key
     * @param method 方法
     * @param params 入参
     * @return 结果
     * @since 0.0.1
     */
    String getMethodId(final Method method, final Object[] params);

}
