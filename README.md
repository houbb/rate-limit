# 项目简介

[rate-limit](https://github.com/houbb/rate-limit) 是一个为 java 设计的限流工具。

目的是为了深入学习和使用限流，后续将会持续迭代。

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.houbb/rate-limit/badge.svg)](http://mvnrepository.com/artifact/com.github.houbb/rate-limit)
[![Build Status](https://www.travis-ci.org/houbb/rate-limit.svg?branch=master)](https://www.travis-ci.org/houbb/rate-limit?branch=master)
[![](https://img.shields.io/badge/license-Apache2-FF0080.svg)](https://github.com/houbb/rate-limit/blob/master/LICENSE.txt)
[![Open Source Love](https://badges.frapsoft.com/os/v2/open-source.svg?v=103)](https://github.com/houbb/rate-limit)

## 特性

- 支持限制访问频率

- 支持限制固定时间内次数(暂时使用固定时间，不够平滑)

- 支持全局模式、ThreadLocal 模式

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

- GlobalLimitFrequencyTest.java

全局的限制访问频率。每次访问间隔为 2S。

```java
/**
 * 全局-限制访问频率
 * Created by bbhou on 2017/11/2.
 */
public class GlobalLimitFrequencyTest {

    private static final Log log = LogFactory.getLog(GlobalLimitFrequencyTest.class);

    /**
     * 2S 访问一次
     */
    private static Limit LIMIT = new GlobalLimitFrequency(TimeUnit.SECONDS, 2);

    static class LimitRunnable implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 4; i++) {
                LIMIT.limit();
                log.info("{}-{}", Thread.currentThread().getName(), i);
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new LimitRunnable()).start();
        new Thread(new LimitRunnable()).start();
    }

}
```

- 日志

```
23:18:46.466 [Thread-2] INFO  com.github.houbb.rate.limit.test.core.GlobalLimitFrequencyTest - Thread-2-0
23:18:48.469 [Thread-1] INFO  com.github.houbb.rate.limit.test.core.GlobalLimitFrequencyTest - Thread-1-0
23:18:50.470 [Thread-2] INFO  com.github.houbb.rate.limit.test.core.GlobalLimitFrequencyTest - Thread-2-1
23:18:52.473 [Thread-1] INFO  com.github.houbb.rate.limit.test.core.GlobalLimitFrequencyTest - Thread-1-1
23:18:54.478 [Thread-2] INFO  com.github.houbb.rate.limit.test.core.GlobalLimitFrequencyTest - Thread-2-2
23:18:56.480 [Thread-1] INFO  com.github.houbb.rate.limit.test.core.GlobalLimitFrequencyTest - Thread-1-2
23:18:58.484 [Thread-2] INFO  com.github.houbb.rate.limit.test.core.GlobalLimitFrequencyTest - Thread-2-3
23:19:00.487 [Thread-1] INFO  com.github.houbb.rate.limit.test.core.GlobalLimitFrequencyTest - Thread-1-3
```

## 功能说明

### 模式说明

- Global 全局模式

在一个方法中，锁是线程间共享的。线程必须等待其他线程执行完成后，获取锁方可执行。

- ThreadLocal 模式

在一个方法中，锁是方法间独立的。

每一个线程的访问控制互不影响。

### 指定时间内的次数

- GlobalLimitCount.java

```java
/**
 * 构造器
 * @param timeUnit 时间单位
 * @param interval 时间间隔
 * @param count 访问次数
 */
public GlobalLimitCount(TimeUnit timeUnit, long interval, int count) {
}
```

- ThreadLocalLimitCount.java

```java
/**
 * 构造器
 * @param timeUnit 时间单位
 * @param interval 时间间隔
 * @param count 访问次数
 */
public ThreadLocalLimitCount(TimeUnit timeUnit, long interval, int count) {
}
```


### 固定访问间隔

- ThreadLocalLimitFrequency.java

```java
/**
 * 构造器
 * @param timeUnit 时间单位
 * @param interval 时间间隔
 */
public ThreadLocalLimitFrequency(TimeUnit timeUnit, long interval) {
}
```

- GlobalLimitFrequency.java

```java
/**
 * 构造器
 * @param timeUnit 时间单位
 * @param interval 时间间隔
 */
public GlobalLimitFrequency(TimeUnit timeUnit, long interval) {
}
```

## 测试案例

| 序号 | 功能 | 测试类 |
|:---|:---|:---|
| 1 | `GlobalLimitCount` | [GlobalLimitCountTest](rate-limit-test/src/test/java/com/github/houbb/rate/limit/test/core/GlobalLimitCountTest.java)|
| 2 | `ThreadLocalLimitCount` | [ThreadLocalLimitCountTest](rate-limit-test/src/test/java/com/github/houbb/rate/limit/test/core/ThreadLocalLimitCountTest.java)|
| 3 | `GlobalLimitFrequency` | [GlobalLimitFrequencyTest](rate-limit-test/src/test/java/com/github/houbb/rate/limit/test/core/GlobalLimitFrequencyTest.java)|
| 4 | `ThreadLocalLimitFrequency` | [ThreadLocalLimitFrequencyTest](rate-limit-test/src/test/java/com/github/houbb/rate/limit/test/core/ThreadLocalLimitFrequencyTest.java)|

# spring 整合

## maven 导入

```xml
<dependency>
    <groupId>com.github.houbb</groupId>
    <artifactId>rate-limit-spring</artifactId>
    <version>${最新版本}</version>
</dependency>
```

## 使用案例

- UserService.java

```java
@Service
public class UserService {

    private static final Log log = LogFactory.getLog(UserService.class);

    @LimitFrequency(interval = 2)
    public void limitFrequencyThreadLocal() {
        log.info("{}", Thread.currentThread().getName());
    }

    @LimitFrequency(interval = 2, limitMode = LimitModeEnum.GLOBAL)
    public void limitFrequencyGlobal(final long id) {
        log.info("{}", Thread.currentThread().getName());
    }

    @LimitCount(interval = 2, count = 5)
    public void limitCountThreadLocal() {
        log.info("{}", Thread.currentThread().getName());
    }

    @LimitCount(interval = 2, count = 5, limitMode = LimitModeEnum.GLOBAL)
    public void limitCountGlobal() {
        log.info("{}", Thread.currentThread().getName());
    }

}
```

## 注解说明 

- `@LimitCount`

```java
public @interface LimitCount {

    /**
     * 时间单位, 默认为秒
     * @see TimeUnit 时间单位
     * @return 时间单位
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 时间间隔
     * (1) 需要填入正整数。
     * @return 时间间隔
     */
    long interval();

    /**
     * 调用次数。
     * (1) 需要填入正整数。
     * @return 调用次数
     */
    int count();

    /**
     * 限制模式
     * 1. 默认为每个线程都是独立的。
     * @return 限制模式
     */
    LimitModeEnum limitMode() default LimitModeEnum.THREAD_LOCAL;

}
```

- `@LimitFrequency`

```java
public @interface LimitFrequency {

    /**
     * 时间单位, 默认为秒
     * @return 时间单位
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 时间间隔, 默认为无。
     * (1) 时间间隔应该为正整数。其他一律视为无时间间隔。
     * @return 时间间隔
     */
    long interval();

    /**
     * 限制模式
     * 1. 默认为每个线程都是独立的。
     * @see LimitModeEnum 限制模式枚举
     * @return 限制模式
     */
    LimitModeEnum limitMode() default LimitModeEnum.THREAD_LOCAL;

}
```

## 测试案例

[SpringConfigTest](rate-limit-test/src/test/java/com/github/houbb/rate/limit/test/config/SpringConfigTest.java)

# 后期 Road-MAP

- [ ] 优化 spring 整合

- [ ] 添加固定时间窗口算法+漏桶算法+令牌筒算法

- [ ] 更多灵活可配置的统计维度(用户标识等)

- [ ] 更加优雅灵活的创建方式 

- [ ] 添加限流策略，忽略、沉睡、降级