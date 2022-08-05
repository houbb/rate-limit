package com.github.houbb.rate.limit.core.core;

import com.github.houbb.rate.limit.api.core.IRateLimit;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
public final class RateLimits {

    private RateLimits(){}

    /**
     * 固定窗口
     * @return 策略
     */
    public static IRateLimit fixedWindow() {
        return new RateLimitFixedWindow();
    }

    /**
     * 滑动窗口
     * @return 策略
     */
    public static IRateLimit slideWindow() {
        return new RateLimitSlideWindow();
    }

    /**
     * 滑动窗口
     * @param windowNum 窗口数量
     * @return 策略
     */
    public static IRateLimit slideWindow(int windowNum) {
        return new RateLimitSlideWindow(windowNum);
    }

    /**
     * 滑动窗口队列实现
     * @return 策略
     */
    public static IRateLimit slideWindowQueue() {
        return new RateLimitSlideWindowQueue();
    }

    /**
     * 漏桶算法
     * @return 策略
     */
    public static IRateLimit leakyBucket() {
        return new RateLimitLeakyBucket();
    }

    /**
     * 令牌桶算法
     * @return 策略
     */
    public static IRateLimit tokenBucket() {
        return new RateLimitTokenBucket();
    }

}
