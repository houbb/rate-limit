package com.github.houbb.rate.limit.spring.annotation;

import com.github.houbb.rate.limit.spring.config.LimitConfig;
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
@Import(LimitConfig.class)
@EnableAspectJAutoProxy
public @interface EnableLimit {
}
