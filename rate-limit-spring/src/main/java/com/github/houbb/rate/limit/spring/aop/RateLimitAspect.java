/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-tryAcquire All rights reserved.
 */

package com.github.houbb.rate.limit.spring.aop;

import com.github.houbb.aop.spring.util.SpringAopUtil;
import com.github.houbb.rate.limit.core.annotation.RateLimit;
import com.github.houbb.rate.limit.core.bs.RateLimitBs;
import org.apiguardian.api.API;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * <p> 切面实现 </p>
 *
 * <pre> Created: 2018/8/5 下午9:37  </pre>
 * <pre> Project: rate-tryAcquire  </pre>
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
    private RateLimitBs rateLimitBs;

    /**
     * 指定注解的方法
     */
    @Pointcut("@annotation(com.github.houbb.rate.limit.core.annotation.RateLimit) || @annotation(com.github.houbb.rate.limit.core.annotation.RateLimits)")
    public void methodMyPointcut() {
    }

    /**
     * 指定注解的类
     */
    @Pointcut("@within(com.github.houbb.rate.limit.core.annotation.RateLimit) || @within(com.github.houbb.rate.limit.core.annotation.RateLimits)")
    public void classMyPointcut() {
    }

    /**
     * https://www.cnblogs.com/bjlhx/p/12081300.html 声明方式
     * @param point 切面
     * @return 结果
     * @throws Throwable 异常
     */
    @Around("methodMyPointcut() || classMyPointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Method method = SpringAopUtil.getCurrentMethod(point);
        // 执行代理操作
        Object[] args = point.getArgs();

        // 核心处理方法
        rateLimitBs.tryAcquire(method, args);

        return point.proceed();
    }

}
