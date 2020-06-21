/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-limit All rights reserved.
 */

package com.github.houbb.rate.limit.test.spring;


import com.github.houbb.rate.limit.test.core.config.SpringConfig;
import com.github.houbb.rate.limit.test.core.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
//@SpringJUnitConfig
//@ContextConfiguration(classes = {SpringConfig.class})
@ContextConfiguration(classes = SpringConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringConfigTest {

    @Autowired
    private UserService userService;

    @Test
    public void limitCountSlideTest() {
        for(int i = 0; i < 5; i++) {
            userService.limitCountSlide();
        }
    }

    @Test
    public void limitFrequencySlideTest() {
        for(int i = 0; i < 10; i++) {
            userService.limitFrequencySlide();
        }
    }


}
