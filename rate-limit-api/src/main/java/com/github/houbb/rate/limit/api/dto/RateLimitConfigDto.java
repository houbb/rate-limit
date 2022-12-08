package com.github.houbb.rate.limit.api.dto;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
public class RateLimitConfigDto implements Serializable {

    /**
     * 每次访问消耗的令牌数
     * @since 1.0.0
     */
    private int permits;

    /**
     * 单位
     * @since 0.0.3
     */
    private TimeUnit timeUnit;

    /**
     * 时间间隔
     * @since 0.0.3
     */
    private long interval;

    /**
     * 次数
     * @since 0.0.3
     */
    private Long count;

    /**
     * 是否启用
     * @since 1.1.0
     */
    private boolean enable;

    public int getPermits() {
        return permits;
    }

    public void setPermits(int permits) {
        this.permits = permits;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public String toString() {
        return "RateLimitConfigDto{" +
                "permits=" + permits +
                ", timeUnit=" + timeUnit +
                ", interval=" + interval +
                ", count=" + count +
                ", enable=" + enable +
                '}';
    }

}
