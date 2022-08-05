package com.github.houbb.rate.limit.api.support;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
public interface IRateLimitTokenService {

    /**
     * 根据入参构建对应的 token
     * @param params 从请求入参中获取信息
     * @return 结果
     * @since 1.0.0
     */
    String getTokenId(final Object[] params);

}
