package com.github.houbb.rate.limit.spring.config;

import com.github.houbb.common.cache.api.service.ICommonCacheService;
import com.github.houbb.common.cache.core.service.CommonCacheServiceMap;
import com.github.houbb.rate.limit.api.core.IRateLimit;
import com.github.houbb.rate.limit.api.support.IRateLimitConfigService;
import com.github.houbb.rate.limit.api.support.IRateLimitMethodService;
import com.github.houbb.rate.limit.api.support.IRateLimitRejectListener;
import com.github.houbb.rate.limit.api.support.IRateLimitTokenService;
import com.github.houbb.rate.limit.core.bs.RateLimitBs;
import com.github.houbb.rate.limit.core.core.RateLimits;
import com.github.houbb.rate.limit.core.support.config.RateLimitConfigService;
import com.github.houbb.rate.limit.core.support.method.RateLimitMethodService;
import com.github.houbb.rate.limit.core.support.reject.RateLimitRejectListenerException;
import com.github.houbb.rate.limit.core.support.token.RateLimitTokenService;
import com.github.houbb.rate.limit.spring.annotation.EnableRateLimit;
import com.github.houbb.timer.api.ITimer;
import com.github.houbb.timer.core.util.Timers;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 限流配置
 *
 * https://blog.csdn.net/u013905744/article/details/91364736
 * @author binbin.hou
 * @since 0.0.3
 */
@Configuration
@ComponentScan(basePackages = "com.github.houbb.rate.limit.spring")
public class RateLimitConfig implements ImportAware, BeanFactoryPostProcessor {

    /**
     * 属性信息
     */
    private AnnotationAttributes enableAttributes;

    /**
     * bean 工厂
     *
     * @since 0.0.5
     */
    private ConfigurableListableBeanFactory beanFactory;

    @Bean("rateLimitBs")
    public RateLimitBs rateLimitBs() {
        IRateLimit rateLimit = beanFactory.getBean(enableAttributes.getString("value"), IRateLimit.class);
        ITimer timer = beanFactory.getBean(enableAttributes.getString("timer"), ITimer.class);
        ICommonCacheService cacheService = beanFactory.getBean(enableAttributes.getString("cacheService"), ICommonCacheService.class);
        IRateLimitConfigService configService = beanFactory.getBean(enableAttributes.getString("configService"), IRateLimitConfigService.class);
        IRateLimitTokenService tokenService = beanFactory.getBean(enableAttributes.getString("tokenService"), IRateLimitTokenService.class);
        IRateLimitMethodService methodService = beanFactory.getBean(enableAttributes.getString("methodService"), IRateLimitMethodService.class);
        IRateLimitRejectListener rejectListener = beanFactory.getBean(enableAttributes.getString("rejectListener"), IRateLimitRejectListener.class);
        String cacheKeyNamespace = enableAttributes.getString("cacheKeyNamespace");

        return RateLimitBs.newInstance()
                .rateLimit(rateLimit)
                .timer(timer)
                .cacheService(cacheService)
                .configService(configService)
                .tokenService(tokenService)
                .methodService(methodService)
                .rejectListener(rejectListener)
                .cacheKeyNamespace(cacheKeyNamespace)
                ;
    }


    @Bean("rateLimit")
    public IRateLimit rateLimit() {
        return RateLimits.tokenBucket();
    }

    @Bean("rateLimitTimer")
    public ITimer rateLimitTimer() {
        return Timers.system();
    }

    @Bean("rateLimitCacheService")
    public ICommonCacheService rateLimitCacheService() {
        return new CommonCacheServiceMap();
    }

    @Bean("rateLimitConfigService")
    public IRateLimitConfigService rateLimitConfigService() {
        return new RateLimitConfigService();
    }

    @Bean("rateLimitTokenService")
    public IRateLimitTokenService rateLimitTokenService() {
        return new RateLimitTokenService();
    }

    @Bean("rateLimitMethodService")
    public IRateLimitMethodService rateLimitMethodService() {
        return new RateLimitMethodService();
    }

    @Bean("rateLimitRejectListener")
    public IRateLimitRejectListener rateLimitRejectListener() {
        return new RateLimitRejectListenerException();
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setImportMetadata(AnnotationMetadata annotationMetadata) {
        enableAttributes = AnnotationAttributes.fromMap(
                annotationMetadata.getAnnotationAttributes(EnableRateLimit.class.getName(), false));
        if (enableAttributes == null) {
            throw new IllegalArgumentException(
                    "@EnableRateLimit is not present on importing class " + annotationMetadata.getClassName());
        }
    }

}
