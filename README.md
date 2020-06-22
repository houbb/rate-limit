# 项目简介

[rate-limit](https://github.com/houbb/rate-limit) 是一个为 java 设计的限流工具。

目的是为了深入学习和使用限流，后续将会持续迭代。

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.houbb/rate-limit/badge.svg)](http://mvnrepository.com/artifact/com.github.houbb/rate-limit)
[![Build Status](https://www.travis-ci.org/houbb/rate-limit.svg?branch=master)](https://www.travis-ci.org/houbb/rate-limit?branch=master)
[![](https://img.shields.io/badge/license-Apache2-FF0080.svg)](https://github.com/houbb/rate-limit/blob/master/LICENSE.txt)
[![Open Source Love](https://badges.frapsoft.com/os/v2/open-source.svg?v=103)](https://github.com/houbb/rate-limit)

## 特性

- 支持限制访问频率

- 支持多种限流策略

- 支持 spring 注解的使用方式

# 变更日志

> [CHANGELOG](CHANGELOG.md)

# 快速开始

## 需求

- jdk 1.7 

- maven 3.x+

## maven 导入

```xml
<dependency>
    <groupId>com.github.houbb</groupId>
    <artifactId>rate-limit-core</artifactId>
    <version>${最新版本}</version>
</dependency>
```

## 演示代码

- LimitFrequencyFixedWindowTest.java

固定时间窗口实现的频率限制：

```java
public class LimitFrequencyFixedWindowTest {

    private static final Log log = LogFactory.getLog(LimitFrequencyFixedWindowTest.class);

    /**
     * 2S 内最多运行 5 次
     * @since 0.0.5
     */
    private static final ILimit LIMIT = LimitBs.newInstance()
            .interval(1)
            .limit(LimitFrequencyFixedWindow.class)
            .build();

    static class LimitRunnable implements Runnable {
        @Override
        public void run() {
            for(int i = 0; i < 5; i++) {
                LIMIT.limit();
                log.info("{}-{}", Thread.currentThread().getName(), i);
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new LimitRunnable()).start();
    }

}
```


- 日志

```
19:41:01.661 [Thread-1] INFO  com.github.houbb.rate.limit.core.core.impl.LimitFixedInterval - [Limit] fixed frequency notify all
19:41:01.667 [Thread-2] INFO  com.github.houbb.rate.limit.test.core.LimitFrequencyFixedWindowTest - Thread-2-0
19:41:02.991 [Thread-1] INFO  com.github.houbb.rate.limit.core.core.impl.LimitFixedInterval - [Limit] fixed frequency notify all
19:41:02.991 [Thread-2] INFO  com.github.houbb.rate.limit.test.core.LimitFrequencyFixedWindowTest - Thread-2-1
19:41:04.321 [Thread-1] INFO  com.github.houbb.rate.limit.core.core.impl.LimitFixedInterval - [Limit] fixed frequency notify all
19:41:04.321 [Thread-2] INFO  com.github.houbb.rate.limit.test.core.LimitFrequencyFixedWindowTest - Thread-2-2
19:41:05.652 [Thread-1] INFO  com.github.houbb.rate.limit.core.core.impl.LimitFixedInterval - [Limit] fixed frequency notify all
19:41:05.652 [Thread-2] INFO  com.github.houbb.rate.limit.test.core.LimitFrequencyFixedWindowTest - Thread-2-3
19:41:06.983 [Thread-1] INFO  com.github.houbb.rate.limit.core.core.impl.LimitFixedInterval - [Limit] fixed frequency notify all
19:41:06.983 [Thread-2] INFO  com.github.houbb.rate.limit.test.core.LimitFrequencyFixedWindowTest - Thread-2-4
```

# 拓展阅读

[spring 整合使用](doc/user/02-spring.md)

# 后期 Road-MAP

- [ ] tryAcquire(timeout) 等新方法加入

- [ ] 分布式限流，对于数据统计的抽象。可以基于 redis/mysql 等

- [ ] 添加限流策略，忽略、沉睡、降级

- [ ] 更多灵活可配置的统计维度(用户标识等)

