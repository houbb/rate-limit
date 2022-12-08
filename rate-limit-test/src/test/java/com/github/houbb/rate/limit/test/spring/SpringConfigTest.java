/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-tryAcquire All rights reserved.
 */

package com.github.houbb.rate.limit.test.spring;


import com.github.houbb.rate.limit.core.exception.RateLimitRuntimeException;
import com.github.houbb.rate.limit.test.core.config.SpringConfig;
import com.github.houbb.rate.limit.test.core.service.ClassUserService;
import com.github.houbb.rate.limit.test.core.service.RepeatClassUserService;
import com.github.houbb.rate.limit.test.core.service.RepeatUserService;
import com.github.houbb.rate.limit.test.core.service.UserService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

/**
 * <p> </p>
 *
 * <pre> Created: 2018/8/1 上午9:53  </pre>
 * <pre> Project: spring-framework-learn  </pre>
 *
 * @author houbinbin
 * @version 0.0.1
 * @since 0.0.1
 */
@ContextConfiguration(classes = SpringConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Ignore
public class SpringConfigTest {

    @Autowired
    private UserService userService;

    @Autowired
    private ClassUserService classUserService;

    @Autowired
    private RepeatClassUserService repeatClassUserService;

    @Autowired
    private RepeatUserService repeatUserService;


    @Test(expected = RateLimitRuntimeException.class)
    public void repeatTest() {
        for(int i = 0; i < 3; i++) {
            repeatUserService.limitCount();
        }
    }

    @Test
    public void repeatPassTest() throws InterruptedException {
        for(int i = 0; i < 3; i++) {
            TimeUnit.SECONDS.sleep(1);
            repeatUserService.limitCount();
        }
    }

    @Test(expected = RateLimitRuntimeException.class)
    public void repeatClassTest() {
        for(int i = 0; i < 3; i++) {
            repeatClassUserService.limitCount();
        }
    }

    @Test
    public void repeatClassPassTest() throws InterruptedException {
        for(int i = 0; i < 3; i++) {
            TimeUnit.SECONDS.sleep(1);
            repeatClassUserService.limitCount();
        }
    }

    @Test(expected = RateLimitRuntimeException.class)
    public void limitCountErrorTest() {
        for(int i = 0; i < 3; i++) {
            userService.limitCount();
        }
    }

    @Test
    public void limitCountPassTest() throws InterruptedException {
        for(int i = 0; i < 3; i++) {
            TimeUnit.SECONDS.sleep(1);
            userService.limitCount();
        }
    }

    /**
     * @since 1.1.0
     */
    @Test(expected = RateLimitRuntimeException.class)
    public void limitCountErrorTest2() {
        for(int i = 0; i < 3; i++) {
            classUserService.limitCount();
        }
    }

    @Test
    public void limitCountClassPassTest() throws InterruptedException {
        for(int i = 0; i < 3; i++) {
            TimeUnit.SECONDS.sleep(1);
            classUserService.limitCount();
        }
    }


}
