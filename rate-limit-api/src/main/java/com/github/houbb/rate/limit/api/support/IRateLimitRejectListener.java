package com.github.houbb.rate.limit.api.support;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
public interface IRateLimitRejectListener {

    /**
     * 监听拒绝的信息
     *
     * 失败时，默认抛出异常。
     *
     * 用户可以结果业务添加对应的报警+黑名单拉黑等操作。
     * @param context 上下文
     * @since 1.0.0
     */
    void listen(final IRateLimitRejectListenerContext context);

}
