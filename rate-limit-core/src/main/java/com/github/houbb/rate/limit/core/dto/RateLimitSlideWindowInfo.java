package com.github.houbb.rate.limit.core.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
public class RateLimitSlideWindowInfo implements Serializable {

    /**
     * 初始化时间
     */
    private long initTime;

    /**
     * 窗口子列表
     */
    private List<RateLimitSlideWindowDto> windowList;

    public long getInitTime() {
        return initTime;
    }

    public void setInitTime(long initTime) {
        this.initTime = initTime;
    }

    public List<RateLimitSlideWindowDto> getWindowList() {
        return windowList;
    }

    public void setWindowList(List<RateLimitSlideWindowDto> windowList) {
        this.windowList = windowList;
    }

    @Override
    public String toString() {
        return "RateLimitSlideWindowInfo{" +
                "initTime=" + initTime +
                ", windowList=" + windowList +
                '}';
    }

}
