/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-tryAcquire All rights reserved.
 */

package com.github.houbb.rate.limit.core.core;

import com.alibaba.fastjson.JSON;
import com.github.houbb.common.cache.api.service.ICommonCacheService;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.rate.limit.api.core.IRateLimitContext;
import com.github.houbb.rate.limit.api.dto.RateLimitConfigDto;
import com.github.houbb.rate.limit.core.dto.RateLimitSlideWindowDto;
import com.github.houbb.rate.limit.core.dto.RateLimitSlideWindowInfo;
import com.github.houbb.timer.api.ITimer;
import org.apiguardian.api.API;

import java.util.ArrayList;
import java.util.List;

/**
 * 滑动窗口限制次数
 *
 * 1. 限制 queue 的大小与 count 一致
 * 2.
 * @author houbinbin
 * Created by bbhou on 2017/9/20.
 * @since 0.0.5
 */
@API(status = API.Status.EXPERIMENTAL)
public class RateLimitSlideWindow extends AbstractRateLimit {

    private static final Log LOG = LogFactory.getLog(RateLimitSlideWindow.class);

    /**
     * 默认切分为10个窗口
     *
     * 后期可以考虑更加灵活的配置，暂时写死。
     * @since 0.0.5
     */
    private final int windowNum;

    public RateLimitSlideWindow(final int windowNum) {
        this.windowNum = windowNum;
    }

    public RateLimitSlideWindow() {
        this(10);
    }

    @Override
    protected boolean doAcquire(String cacheKey, RateLimitConfigDto configDto, IRateLimitContext rateLimitContext) {
        RateLimitSlideWindowInfo slideWindowInfo = queryCacheInfo(cacheKey, configDto, rateLimitContext);

        //计算总数
        long oldSum = calcSum(slideWindowInfo, rateLimitContext);
        final int permits = configDto.getPermits();
        final long limitCount = configDto.getCount();
        long countVal = oldSum + permits;
        if(countVal > limitCount) {
            LOG.warn("[RateLimit] countVal {} is gt than limit {}", countVal, limitCount);
            return false;
        } else {
            // 计算当前的下标
            long initTime = slideWindowInfo.getInitTime();
            long now = rateLimitContext.timer().time();
            long timeWindow = calcTimeWindow(configDto);

            // 根据时间差，计算当前应该在第一个时间窗口上
            int index = (int) (((now - initTime) / timeWindow) % windowNum);

            List<RateLimitSlideWindowDto> windowDtoList = slideWindowInfo.getWindowList();
            RateLimitSlideWindowDto windowDto = windowDtoList.get(index);
            long oldExpireTime = windowDto.getExpireTime();
            // 过期，则清零。
            if(now > oldExpireTime) {
                RateLimitSlideWindowDto newWindowDto = new RateLimitSlideWindowDto();
                // 过期时间为当前时间的基础上+一个时间窗口
                newWindowDto.setExpireTime(now + timeWindow);
                newWindowDto.setCount(permits);
                windowDtoList.set(index, windowDto);
            } else {
                // 没过期，则累加
                int newCount = windowDto.getCount()+permits;
                windowDto.setCount(newCount);
                windowDtoList.set(index, windowDto);
            }

            // 更新到缓存中
            final ICommonCacheService commonCacheService = rateLimitContext.cacheService();
            commonCacheService.set(cacheKey, JSON.toJSONString(slideWindowInfo));

            return true;
        }
    }

    /**
     * 计算历史有效的总和
     * @param slideWindowInfo 信息
     * @param rateLimitContext 上下文
     * @return 结果
     */
    private long calcSum(RateLimitSlideWindowInfo slideWindowInfo,
                         IRateLimitContext rateLimitContext) {
        long sum = 0;
        long now = rateLimitContext.timer().time();

        List<RateLimitSlideWindowDto> windowList = slideWindowInfo.getWindowList();
        for(RateLimitSlideWindowDto windowDto : windowList) {
            if(windowDto == null) {
                continue;
            }

            long expireTime = windowDto.getExpireTime();
            if(now <= expireTime) {
                long count = windowDto.getCount();
                sum += count;
            }
        }

        return sum;
    }


    /**
     * 查询缓存信息
     * @param cacheKey 缓存
     * @param configDto 配置对象
     * @param rateLimitContext 限制上下文
     * @return 结果
     */
    private RateLimitSlideWindowInfo queryCacheInfo(String cacheKey, RateLimitConfigDto configDto, IRateLimitContext rateLimitContext) {
        final ICommonCacheService cacheService = rateLimitContext.cacheService();
        String cacheValue = cacheService.get(cacheKey);
        if(StringUtil.isNotEmpty(cacheValue)) {
            // 反序列化
            return JSON.parseObject(cacheValue, RateLimitSlideWindowInfo.class);
        }

        //1. 获取对应的缓存信息
        long timeWindow = calcTimeWindow(configDto);

        final ITimer timer = rateLimitContext.timer();
        final long now = timer.time();
        List<RateLimitSlideWindowDto> windowList = new ArrayList<>(windowNum);
        for(int i = 0; i < windowNum; i++) {
            RateLimitSlideWindowDto windowDto = new RateLimitSlideWindowDto();
            windowDto.setCount(0);
            // 初始化的过期时间不同
            windowDto.setExpireTime(now + i*timeWindow);
            windowList.add(windowDto);
        }

        RateLimitSlideWindowInfo windowInfo = new RateLimitSlideWindowInfo();
        windowInfo.setInitTime(now);
        windowInfo.setWindowList(windowList);
        return windowInfo;
    }

    /**
     * 计算时间窗口
     * @param configDto 配置
     * @return 结果
     */
    private long calcTimeWindow(RateLimitConfigDto configDto) {
        //1. 获取对应的缓存信息
        long intervalMills = configDto.getTimeUnit().toMillis(configDto.getInterval());
        // 每一个 key 存活的有效期
        return intervalMills / windowNum;
    }

}
