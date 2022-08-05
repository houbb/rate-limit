package com.github.houbb.rate.limit.core.dto;

import java.io.Serializable;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
public class RateLimitTokenBucketDto implements Serializable {

    /**
     * 令牌的发放速率
     * <p>
     * 每一秒发放多少。
     *
     * @since 0.0.6
     */
    private long rate;

    /**
     * 容量
     * <p>
     * 后期暴露为可以配置
     *
     * @since 0.0.6
     */
    private long capacity;

    /**
     * 令牌数量
     *
     * @since 0.0.6
     */
    private volatile long tokenNum;

    /**
     * 上一次的更新时间
     *
     * @since 0.0.6
     */
    private volatile long lastUpdateTime;

    public long getRate() {
        return rate;
    }

    public void setRate(long rate) {
        this.rate = rate;
    }

    public long getCapacity() {
        return capacity;
    }

    public void setCapacity(long capacity) {
        this.capacity = capacity;
    }

    public long getTokenNum() {
        return tokenNum;
    }

    public void setTokenNum(long tokenNum) {
        this.tokenNum = tokenNum;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Override
    public String toString() {
        return "RateLimitBucketDto{" +
                "rate=" + rate +
                ", capacity=" + capacity +
                ", tokenNum=" + tokenNum +
                ", lastUpdateTime=" + lastUpdateTime +
                '}';
    }

}
