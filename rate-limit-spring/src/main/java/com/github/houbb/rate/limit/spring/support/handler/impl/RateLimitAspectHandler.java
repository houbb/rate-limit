package com.github.houbb.rate.limit.spring.support.handler.impl;

import com.github.houbb.heaven.constant.PunctuationConst;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.rate.limit.core.bs.RateLimitBs;
import com.github.houbb.rate.limit.core.core.IRateLimit;
import com.github.houbb.rate.limit.spring.annotation.RateLimit;
import com.github.houbb.rate.limit.spring.support.handler.IRateLimitAspectHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p> project: rate-tryAcquire-LimitHandler </p>
 * <p> create on 2020/6/20 20:47 </p>
 *
 * @author binbin.hou
 * @since 0.0.3
 */
@Component
public class RateLimitAspectHandler implements IRateLimitAspectHandler {

    /**
     * 用来存放方法的限制器
     * Key=方法全名+注解名称
     * @since 0.0.3
     */
    private static final Map<String, IRateLimit> LIMIT_HASH_MAP = new ConcurrentHashMap<>();

    /**
     * 日志
     *
     * @since 0.0.3
     */
    private static final Log LOG = LogFactory.getLog(RateLimitAspectHandler.class);

    /**
     * 处理对应的信息
     *
     * @param method   方法
     * @param rateLimit    限制对象
     * @since 0.0.3
     */
    @Override
    public void handle(final Method method,
                       final RateLimit rateLimit) {
        final Class<? extends IRateLimit> strategy = rateLimit.limitClass();

        String key = getMethodFullName(method) + ":" + strategy.getSimpleName();
        IRateLimit instance = LIMIT_HASH_MAP.get(key);

        if(instance == null) {
            instance = RateLimitBs.newInstance()
                    .limitClass(strategy)
                    .count(rateLimit.count())
                    .timeUnit(rateLimit.timeUnit())
                    .interval(rateLimit.interval())
                    .build();

            LIMIT_HASH_MAP.put(key, instance);
        }


        instance.tryAcquire();
    }

    /**
     * 方法全名此处应该考虑不同的参数问题。
     *
     * @param method 方法
     * @return 完整的方法名称
     * @since 0.0.1
     */
    private static String getMethodFullName(Method method) {
        final String className = method.getDeclaringClass().getName();
        Class[] types = method.getParameterTypes();
        StringBuilder nameBuilder = new StringBuilder(className + "." + method.getName());
        if (ObjectUtil.isNotEmpty(types)) {
            for (Class parameter : types) {
                nameBuilder.append(PunctuationConst.COLON).append(parameter.getName());
            }
        }
        return nameBuilder.toString();
    }

}
