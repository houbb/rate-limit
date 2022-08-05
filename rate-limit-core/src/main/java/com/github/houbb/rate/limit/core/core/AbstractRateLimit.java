package com.github.houbb.rate.limit.core.core;

import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.rate.limit.api.core.IRateLimit;
import com.github.houbb.rate.limit.api.core.IRateLimitContext;
import com.github.houbb.rate.limit.api.dto.RateLimitConfigDto;
import com.github.houbb.rate.limit.api.support.*;
import com.github.houbb.rate.limit.core.support.reject.RateLimitRejectListenerContext;
import com.github.houbb.rate.limit.core.util.InnerRateLimitUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 适配器
 * @author binbin.hou
 * @since 0.0.5
 */
public abstract class AbstractRateLimit implements IRateLimit {

    /**
     * 日志
     *
     * @since 0.0.5
     */
    private static final Log LOG = LogFactory.getLog(AbstractRateLimit.class);

    /**
     * 执行
     * @param cacheKey 缓存标识
     * @param configDto 配置
     * @param rateLimitContext 上下文
     * @return 结果
     * @since 1.0.0
     */
    protected  abstract boolean doAcquire(String cacheKey,
                                         RateLimitConfigDto configDto,
                                         IRateLimitContext rateLimitContext);

    @Override
    public boolean tryAcquire(IRateLimitContext context) {
        //1. 基本信息
        final Method method = context.method();
        final Object[] args = context.args();
        final IRateLimitTokenService tokenService = context.tokenService();
        final IRateLimitMethodService methodService = context.methodService();
        final String tokenId = tokenService.getTokenId(args);
        final String methodId = methodService.getMethodId(method, args);

        //2. 查询配置信息
        final IRateLimitConfigService configService = context.configService();
        List<RateLimitConfigDto> configDtoList = configService.queryConfigList(tokenId, methodId, method);

        //3. 最后的结果
        boolean acquireFlag = false;
        if(CollectionUtil.isEmpty(configDtoList)) {
            LOG.info("method {} 对应的配置为空，不做限制", methodId);
            acquireFlag = true;
        } else {
            acquireFlag = tryAcquire(configDtoList, methodId, tokenId, context);
        }

        final IRateLimitRejectListener rejectListener = context.rejectListener();
        final IRateLimitRejectListenerContext rejectListenerContext = RateLimitRejectListenerContext.newInstance()
                .acquireFlag(acquireFlag)
                .method(method)
                .args(args)
                .rejectListener(rejectListener)
                .tokenService(tokenService)
                .methodService(methodService)
                .configService(configService)
                .cacheService(context.cacheService())
                .configList(configDtoList)
                .timer(context.timer());

        rejectListener.listen(rejectListenerContext);

        return acquireFlag;
    }

    protected boolean tryAcquire(List<RateLimitConfigDto> configDtoList,
                                 String methodId,
                                 String tokenId,
                                 final IRateLimitContext context) {
        // 全部通过则为通过
        final Set<Long> rateSet = new HashSet<>();
        List<Boolean> resultFlagList = new ArrayList<>();

        // 需要全部遍历，不然对应的消耗数据不准
        for(RateLimitConfigDto configDto : configDtoList) {
            // 速率
            Long rate = InnerRateLimitUtils.calcRate(configDto);
            // 主要是避免令牌被重复消费的问题
            if(rateSet.contains(rate)) {
                LOG.info("配置 {} 对应的速率已存在 {}", configDto, rate);
                continue;
            }
            rateSet.add(rate);

            // 构建 key
            String cacheKey = "rateLimit:"+tokenId+":"+methodId+":"+rate;
            // 执行结果
            boolean resultFlag = doAcquire(cacheKey, configDto, context);

            resultFlagList.add(resultFlag);
        }

        // 全部通过，才认为是通过
        return !resultFlagList.contains(Boolean.FALSE);
    }

}
