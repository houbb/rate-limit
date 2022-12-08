package com.github.houbb.rate.limit.api.support;

import com.github.houbb.rate.limit.api.dto.RateLimitConfigDto;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
public interface IRateLimitConfigService {

    /**
     * 获取当前用户，当前方法对应的配置信息
     *
     * ps: 返回配置列表，支持多种不同的限制。
     *
     * 比如 1min 内 10次，1H内 100 次等等。
     *
     * @param tokenId 用户标识
     * @param methodId 方法标识
     * @param method 方法信息
     * @return 结果
     * @since 1.0.0
     */
    List<RateLimitConfigDto> queryConfigList(String tokenId,
                                             String methodId,
                                             Method method);

}
