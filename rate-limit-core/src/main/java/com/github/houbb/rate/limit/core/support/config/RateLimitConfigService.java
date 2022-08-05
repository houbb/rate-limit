package com.github.houbb.rate.limit.core.support.config;

import com.github.houbb.rate.limit.api.dto.RateLimitConfigDto;
import com.github.houbb.rate.limit.api.support.IRateLimitConfigService;
import com.github.houbb.rate.limit.core.annotation.RateLimit;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
public class RateLimitConfigService implements IRateLimitConfigService {

    @Override
    public List<RateLimitConfigDto> queryConfigList(String tokenId, String methodId, Method method) {
        List<RateLimitConfigDto> list = new ArrayList<>();

        // 默认策略
        RateLimit rateLimit = method.getAnnotation(RateLimit.class);
        if(rateLimit != null) {
            RateLimitConfigDto configDto = new RateLimitConfigDto();
            configDto.setCount(rateLimit.count());
            configDto.setInterval(rateLimit.interval());
            configDto.setPermits(rateLimit.value());
            configDto.setTimeUnit(rateLimit.timeUnit());
            list.add(configDto);
        }

        return list;
    }

}
