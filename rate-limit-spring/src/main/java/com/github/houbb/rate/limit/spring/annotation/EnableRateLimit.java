package com.github.houbb.rate.limit.spring.annotation;

import com.github.houbb.rate.limit.spring.config.RateLimitConfig;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用自动限制注解
 * @author binbin.hou
 * @since 0.0.3
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(RateLimitConfig.class)
@EnableAspectJAutoProxy
public @interface EnableRateLimit {

    /**
     * 限流策略
     * @return 限流策略
     */
    String value() default "rateLimit";

    /**
     * 时间策略
     * @return 时间策略
     */
    String timer() default "rateLimitTimer";

    /**
     * 缓存策略
     * @return 缓存策略
     */
    String cacheService() default "rateLimitCacheService";

    /**
     * 配置策略
     * @return 配置策略
     */
    String configService() default "rateLimitConfigService";

    /**
     * 标识服务类
     * @return 标识
     */
    String tokenService() default "rateLimitTokenService";

    /**
     * 方法服务类
     * @return 服务类
     */
    String methodService() default "rateLimitMethodService";

    /**
     * 拒绝策略
     * @return 策略
     */
    String rejectListener() default "rateLimitRejectListener";

}
