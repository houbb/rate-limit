/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-acquire All rights reserved.
 */

package com.github.houbb.rate.limit.spring.aop;

import com.github.houbb.rate.limit.core.exception.RateLimitRuntimeException;
import com.github.houbb.rate.limit.spring.annotation.RateLimit;
import com.github.houbb.rate.limit.spring.support.handler.IRateLimitAspectHandler;
import org.apiguardian.api.API;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * <p> 切面实现 </p>
 *
 * <pre> Created: 2018/8/5 下午9:37  </pre>
 * <pre> Project: rate-acquire  </pre>
 *
 * @author houbinbin
 * @version 0.0.1
 * @since 0.0.1
 */
@Aspect
@Component
@API(status = API.Status.MAINTAINED)
public class RateLimitAspect {

    @Autowired
    private IRateLimitAspectHandler limitHandler;

    @Pointcut("@annotation(com.github.houbb.rate.limit.spring.annotation.RateLimit)")
    public void myPointcut() {
    }

    @Around("myPointcut() && @annotation(rateLimit)")
    public Object around(ProceedingJoinPoint point, RateLimit rateLimit) throws Throwable {
        Method method = getCurrentMethod(point);

        // 核心处理方法
        limitHandler.handle(method, rateLimit);

        return point.proceed();
    }

    /**
     * 获取当前扥方法
     * @param point 切面
     * @return 结果
     * @since 0.0.1
     */
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

}
