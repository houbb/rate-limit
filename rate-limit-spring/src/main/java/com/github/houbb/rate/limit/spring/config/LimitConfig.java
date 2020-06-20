package com.github.houbb.rate.limit.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 自动配置限次配置
 *
 * https://blog.csdn.net/u013905744/article/details/91364736
 * @author binbin.hou
 * @since 0.0.3
 */
@Configuration
@ComponentScan(basePackages = "com.github.houbb.rate.limit.spring")
public class LimitConfig {
}
