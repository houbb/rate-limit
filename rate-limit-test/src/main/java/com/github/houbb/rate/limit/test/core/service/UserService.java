/*
 * Copyright (c)  2018. houbinbin Inc.
 * rate-limit All rights reserved.
 */

package com.github.houbb.rate.limit.test.core.service;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.rate.limit.core.core.impl.LimitCountSlideWindow;
import com.github.houbb.rate.limit.core.core.impl.LimitFrequencySlideWindows;
import com.github.houbb.rate.limit.spring.annotation.Limit;
import org.springframework.stereotype.Service;

/**
 * <p> 服务实现 </p>
 *
 * <pre> Created: 2018/8/5 下午10:53  </pre>
 * <pre> Project: rate-limit  </pre>
 *
 * @author houbinbin
 * @version 0.0.1
 * @since 0.0.1
 */
@Service
public class UserService {

    private static final Log log = LogFactory.getLog(UserService.class);

    @Limit(interval = 2, limit = LimitFrequencySlideWindows.class)
    public void limitFrequencySlide() {
        log.info("{}", Thread.currentThread().getName());
    }

    @Limit(interval = 2, count = 5, limit = LimitCountSlideWindow.class)
    public void limitCountSlide() {
        log.info("{}", Thread.currentThread().getName());
    }

}
