package com.github.houbb.rate.limit.springboot.starter.config;

import com.github.houbb.rate.limit.spring.annotation.EnableRateLimit;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

/**
 * @author binbin.hou
 * @since 0.0.10
 */
@EnableRateLimit
@Configuration
@ConditionalOnClass(EnableRateLimit.class)
public class RateLimitAutoConfig {
}
