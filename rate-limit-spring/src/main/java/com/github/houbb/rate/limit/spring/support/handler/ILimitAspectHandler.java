package com.github.houbb.rate.limit.spring.support.handler;

import com.github.houbb.rate.limit.core.core.ILimit;
import com.github.houbb.rate.limit.spring.annotation.Limit;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * <p> project: rate-limit-LimitHandler </p>
 * <p> create on 2020/6/20 20:47 </p>
 *
 * @author binbin.hou
 * @since 0.0.3
 */
public interface ILimitAspectHandler {

    /**
     * 处理对应的信息
     * @param method 方法
     * @param limit 限制对象
     * @since 0.0.3
     */
    void handle(final Method method,
                final Limit limit);

}
