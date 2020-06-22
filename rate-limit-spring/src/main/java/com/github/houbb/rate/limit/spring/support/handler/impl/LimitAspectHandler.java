package com.github.houbb.rate.limit.spring.support.handler.impl;

import com.github.houbb.heaven.constant.PunctuationConst;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.rate.limit.core.bs.LimitBs;
import com.github.houbb.rate.limit.core.core.ILimit;
import com.github.houbb.rate.limit.spring.annotation.Limit;
import com.github.houbb.rate.limit.spring.support.handler.ILimitAspectHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p> project: rate-acquire-LimitHandler </p>
 * <p> create on 2020/6/20 20:47 </p>
 *
 * @author binbin.hou
 * @since 0.0.3
 */
@Component
public class LimitAspectHandler implements ILimitAspectHandler {

    /**
     * 用来存放方法的限制器
     * Key=方法全名+注解名称
     * @since 0.0.3
     */
    private static final Map<String, ILimit> LIMIT_HASH_MAP = new ConcurrentHashMap<>();

    /**
     * 日志
     *
     * @since 0.0.3
     */
    private static final Log LOG = LogFactory.getLog(LimitAspectHandler.class);

    /**
     * 处理对应的信息
     *
     * @param method   方法
     * @param limit    限制对象
     * @since 0.0.3
     */
    @Override
    public void handle(final Method method,
                       final Limit limit) {
        final Class<? extends ILimit> strategy = limit.limit();

        String key = getMethodFullName(method) + ":" + strategy.getSimpleName();
        ILimit instance = LIMIT_HASH_MAP.get(key);

        if(instance == null) {
            instance = LimitBs.newInstance()
                    .limit(strategy)
                    .count(limit.count())
                    .timeUnit(limit.timeUnit())
                    .interval(limit.interval())
                    .build();

            LIMIT_HASH_MAP.put(key, instance);
        }


        instance.acquire();
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
