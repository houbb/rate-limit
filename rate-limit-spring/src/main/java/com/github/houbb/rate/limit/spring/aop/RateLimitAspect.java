/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-limit All rights reserved.
 */

package com.github.houbb.rate.limit.spring.aop;

import com.github.houbb.rate.limit.core.core.Limit;
import com.github.houbb.rate.limit.core.core.impl.GlobalLimitCount;
import com.github.houbb.rate.limit.core.core.impl.GlobalLimitFrequency;
import com.github.houbb.rate.limit.core.core.impl.ThreadLocalLimitCount;
import com.github.houbb.rate.limit.core.core.impl.ThreadLocalLimitFrequency;
import com.github.houbb.rate.limit.core.exception.RateLimitRuntimeException;
import com.github.houbb.rate.limit.core.util.ArgUtil;
import com.github.houbb.rate.limit.spring.annotation.LimitCount;
import com.github.houbb.rate.limit.spring.annotation.LimitFrequency;
import com.github.houbb.rate.limit.spring.constant.LimitModeEnum;

import org.apiguardian.api.API;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p> </p>
 *
 * <pre> Created: 2018/8/5 下午9:37  </pre>
 * <pre> Project: rate-limit  </pre>
 *
 * @author houbinbin
 * @version 1.0
 * @since JDK 1.7
 */
@Aspect
@Component
@API(status = API.Status.MAINTAINED)
public class RateLimitAspect {

    /**
     * 用来存放方法的限制器
     * Key=方法全名+注解名称
     */
    private Map<String, Limit> limitHashMap = new ConcurrentHashMap<>();

    @Pointcut("@annotation(com.github.houbb.rate.limit.spring.annotation.LimitCount) || " +
                      "@annotation(com.github.houbb.rate.limit.spring.annotation.LimitFrequency)")
    public void myPointcut(){}

    @Around("myPointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Method method = getCurrentMethod(point);
        handleLimitCount(method);
        handleLimitFrequency(method);

        return point.proceed();
    }

    private void handleLimitCount(final Method method) {
        LimitCount limitCount = method.getDeclaredAnnotation(LimitCount.class);
        if(ArgUtil.isNotNull(limitCount)) {
            final String countKey = getMethodFullName(method) + Key.LIMIT_COUNT;
            if(!limitHashMap.containsKey(countKey)) {
                LimitModeEnum limitModeEnum = limitCount.limitMode();
                Limit limitCreate;
                if(LimitModeEnum.isThreadLocal(limitModeEnum)) {
                    limitCreate = new ThreadLocalLimitCount(limitCount.timeUnit(), limitCount.interval(), limitCount.count());
                } else {
                    limitCreate = new GlobalLimitCount(limitCount.timeUnit(), limitCount.interval(), limitCount.count());
                }

                limitHashMap.put(countKey, limitCreate);
            }

            Limit limit = limitHashMap.get(countKey);
            limit.limit();
        }
    }

    private void handleLimitFrequency(final Method method) {
        LimitFrequency limitFrequency = method.getDeclaredAnnotation(LimitFrequency.class);

        if(ArgUtil.isNotNull(limitFrequency)) {
            final String countKey = getMethodFullName(method) + Key.LIMIT_FREQUENCY;
            if(!limitHashMap.containsKey(countKey)) {
                LimitModeEnum limitModeEnum = limitFrequency.limitMode();
                Limit limitCreate;
                if(LimitModeEnum.isThreadLocal(limitModeEnum)) {
                    limitCreate = new ThreadLocalLimitFrequency(limitFrequency.timeUnit(), limitFrequency.interval());
                } else {
                    limitCreate = new GlobalLimitFrequency(limitFrequency.timeUnit(), limitFrequency.interval());
                }

                limitHashMap.put(countKey, limitCreate);
            }

            Limit limit = limitHashMap.get(countKey);
            limit.limit();
        }
    }

    private interface Key {
        String LIMIT_COUNT = "limitCount";
        String LIMIT_FREQUENCY = "limitCount";
    }

    private Method getCurrentMethod(ProceedingJoinPoint point) {
        try {
            Signature sig = point.getSignature();
            MethodSignature msig = (MethodSignature) sig;
            Object target = point.getTarget();
            return target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        } catch (NoSuchMethodException e) {
            throw new RateLimitRuntimeException(e);
        }
    }

    /**
     * 方法全名此处应该考虑不同的参数问题。
     * TODO: 抽象此工具类
     * @param method 方法
     * @return 完整的方法名称
     */
    private String getMethodFullName(Method method) {
        final String className = method.getDeclaringClass().getName();
        return className + "." + method.getName();
    }

}
