package com.github.houbb.rate.limit.core.dto;

import java.io.Serializable;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
public class RateLimitSlideWindowDto implements Serializable {

    /**
     * 当前元素的总数
     */
    private int count;

    /**
     * 过期时间
     */
    private long expireTime;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public String toString() {
        return "RateLimitSlideWindowDto{" +
                "count=" + count +
                ", expireTime=" + expireTime +
                '}';
    }
}
