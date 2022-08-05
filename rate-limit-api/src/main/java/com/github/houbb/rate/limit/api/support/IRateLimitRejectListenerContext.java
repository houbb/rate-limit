package com.github.houbb.rate.limit.api.support;

import com.github.houbb.rate.limit.api.core.IRateLimitContext;
import com.github.houbb.rate.limit.api.dto.RateLimitConfigDto;

import java.util.List;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
public interface IRateLimitRejectListenerContext extends IRateLimitContext {

    /**
     * 访问标识
     * @return 标识
     */
    String tokenId();

    /**
     * 方法标识
     * @return 标识
     */
    String methodId();

    /**
     * 配置列表
     * @return 列表
     * @since 1.0.0
     */
    List<RateLimitConfigDto> configList();

    /**
     * 最后的确认结果
     * @return 结果
     */
    boolean acquireFlag();

}
