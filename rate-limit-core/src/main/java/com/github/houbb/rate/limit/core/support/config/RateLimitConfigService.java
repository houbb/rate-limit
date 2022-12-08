package com.github.houbb.rate.limit.core.support.config;

import com.github.houbb.heaven.util.util.ArrayUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.rate.limit.api.dto.RateLimitConfigDto;
import com.github.houbb.rate.limit.api.support.IRateLimitConfigService;
import com.github.houbb.rate.limit.core.annotation.RateLimit;
import com.github.houbb.rate.limit.core.annotation.RateLimits;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
public class RateLimitConfigService implements IRateLimitConfigService {

    @Override
    public List<RateLimitConfigDto> queryConfigList(String tokenId, String methodId, Method method) {
        //1. 方法上的优先级较高，如果存在一个，则直接忽略类上的。避免混乱。
        RateLimit methodRateLimit = method.getAnnotation(RateLimit.class);
        RateLimits methodRateLimits = method.getAnnotation(RateLimits.class);
        List<RateLimitConfigDto> methodConfigList = buildConfigList(methodRateLimit, methodRateLimits);
        if(CollectionUtil.isNotEmpty(methodConfigList)) {
            return methodConfigList;
        }

        //2. 类上的信息
        final Class<?> clazz = method.getDeclaringClass();
        RateLimit clazzRateLimit = clazz.getAnnotation(RateLimit.class);
        RateLimits clazzRateLimits = clazz.getAnnotation(RateLimits.class);
        List<RateLimitConfigDto> clazzConfigList = buildConfigList(clazzRateLimit, clazzRateLimits);
        if(CollectionUtil.isNotEmpty(clazzConfigList)) {
            return clazzConfigList;
        }

        //3. 返回空
        return Collections.emptyList();
    }

    /**
     * 构建列表
     * @param rateLimit 费率
     * @param rateLimits 限制列表
     * @return 结果
     * @since 1.1.0
     */
    private List<RateLimitConfigDto> buildConfigList(RateLimit rateLimit,
                                                     RateLimits rateLimits) {
        List<RateLimitConfigDto> resultList = new ArrayList<>();

        //1. 注解
        RateLimitConfigDto rateLimitConfig = buildConfig(rateLimit);
        if(rateLimitConfig != null) {
            resultList.add(rateLimitConfig);
        }

        //2. 重复注解
        if(rateLimits != null) {
            RateLimit[] rateLimitsArray = rateLimits.value();
            if(ArrayUtil.isNotEmpty(rateLimitsArray)) {
                for(RateLimit limit : rateLimitsArray) {
                    RateLimitConfigDto configDto = buildConfig(limit);
                    if(configDto != null) {
                        resultList.add(configDto);
                    }
                }
            }
        }

        //3. 返回结果
        return resultList;
    }

    /**
     * 配置构建
     * @param rateLimit 列表
     * @return 结果
     * @since 1.1.0
     */
    private RateLimitConfigDto buildConfig(RateLimit rateLimit) {
        if(rateLimit == null) {
            return null;
        }

        RateLimitConfigDto configDto = new RateLimitConfigDto();
        configDto.setCount(rateLimit.count());
        configDto.setInterval(rateLimit.interval());
        configDto.setPermits(rateLimit.value());
        configDto.setTimeUnit(rateLimit.timeUnit());
        configDto.setEnable(rateLimit.enable());

        return configDto;
    }

}
