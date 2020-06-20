/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-limit All rights reserved.
 */

package com.github.houbb.rate.limit.test.core.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 *
 * @author bbhou
 * @since 0.0.1
 */
@Configuration
@ComponentScan(SpringConfig.PATH)
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SpringConfig {

    public static final String PATH = "com.github.houbb.rate.limit";

}

