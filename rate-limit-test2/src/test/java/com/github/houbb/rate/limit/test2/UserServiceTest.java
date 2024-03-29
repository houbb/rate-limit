package com.github.houbb.rate.limit.test2;

import com.github.houbb.rate.limit.core.exception.RateLimitRuntimeException;
import com.github.houbb.rate.limit.test2.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author binbin.hou
 * @since 0.0.10
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RateLimitApplication.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test(expected = RateLimitRuntimeException.class)
    public void limitCountErrorTest() {
        for(int i = 0; i < 3; i++) {
            userService.limitCount();
        }
    }

}
