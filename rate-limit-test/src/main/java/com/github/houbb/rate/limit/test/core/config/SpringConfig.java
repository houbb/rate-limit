/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-tryAcquire All rights reserved.
 */

package com.github.houbb.rate.limit.test.core.config;


import com.github.houbb.rate.limit.spring.annotation.EnableRateLimit;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author bbhou
 * @since 0.0.1
 */
@Configuration
@ComponentScan("com.github.houbb.rate.limit.test.core")
@EnableRateLimit
public class SpringConfig {

}

