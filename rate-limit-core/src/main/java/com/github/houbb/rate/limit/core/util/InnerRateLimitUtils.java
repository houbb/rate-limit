package com.github.houbb.rate.limit.core.util;

import com.github.houbb.rate.limit.api.dto.RateLimitConfigDto;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
public class InnerRateLimitUtils {

    private InnerRateLimitUtils(){}

    /**
     * 计算速率
     * @param configDto 配置
     * @return 结果
     */
    public static Long calcRate(RateLimitConfigDto configDto) {
        // 暂不考虑特殊输入，比如 1s 令牌少于1 的场景
        long intervalSeconds = configDto.getTimeUnit().toSeconds(configDto.getInterval());
        // 速率
        return Math.max(1, configDto.getCount() / intervalSeconds);
    }

}
