package com.github.houbb.rate.limit.core.dto;

import java.io.Serializable;

/**
 * 漏桶算法
 *
 * @author binbin.hou
 * @since 1.0.0
 */
public class RateLimitLeakyBucketDto implements Serializable {

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
     * 水量
     *
     * @since 0.0.6
     */
    private volatile long water;

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

    public long getWater() {
        return water;
    }

    public void setWater(long water) {
        this.water = water;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Override
    public String toString() {
        return "RateLimitLeakyBucketDto{" +
                "rate=" + rate +
                ", capacity=" + capacity +
                ", water=" + water +
                ", lastUpdateTime=" + lastUpdateTime +
                '}';
    }

}
