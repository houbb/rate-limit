# 项目简介

[rate-limit](https://github.com/houbb/rate-limit) 是一个为 java 设计的渐进式限流工具。

目的是为了深入学习和使用限流，后续将会持续迭代。

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.houbb/rate-limit/badge.svg)](http://mvnrepository.com/artifact/com.github.houbb/rate-limit)
[![Build Status](https://www.travis-ci.org/houbb/rate-limit.svg?branch=master)](https://www.travis-ci.org/houbb/rate-limit?branch=master)
[![](https://img.shields.io/badge/license-Apache2-FF0080.svg)](https://github.com/houbb/rate-limit/blob/master/LICENSE.txt)
[![Open Source Love](https://badges.frapsoft.com/os/v2/open-source.svg?v=103)](https://github.com/houbb/rate-limit)

## 特性

- 渐进式实现

- 支持独立于 spring 使用

- 支持整合 spring

- 支持整合 spring-boot

- 内置多种限流策略

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
    <version>1.0.0</version>
</dependency>
```

## 入门例子

### 方法定义

`@RateLimit` 限流注解放在方法上，指定对应的限制频率。

| 属性 | 说明 | 默认值 |
|:---|:---|:---|
| value | 方法访问一次消耗的令牌数 | `1` |
| timeUnit | 时间单位 | `TimeUnit.SECONDS` |
| interval | 时间间隔 | `60` |
| count | 可调用次数 | `1000` |

默认为 60S 内，可以调用 1000 次。

```java
public class UserService {

    @RateLimit(interval = 2, count = 5)
    public void limitCount() {
        log.info("{}", Thread.currentThread().getName());
    }

}
```

这个例子中我们 2S 内最多调用 5 次。

### 代码测试

`RateLimitProxy.getProxy(xxx)` 通过字节码获取方法对应的方法代理。

```java
@Test(expected = RateLimitRuntimeException.class)
public void limitCountErrorTest() {
    UserService userService = RateLimitProxy.getProxy(new UserService());
    for(int i = 0; i < 3; i++) {
        userService.limitCount();
    }
}
```

当调用超出限制时，默认抛出 `RateLimitRuntimeException` 异常。

这里默认使用的是令牌桶算法，所以会出现异常。

### 指定引导类

```java
RateLimitProxy.getProxy(new UserService());
```

等价于 

```java
RateLimitProxy.getProxy(new UserService(), RateLimitBs.newInstance());
```

下面我们来一起看一下 RateLimitBs 引导类。

## 引导类

`RateLimitBs` 作为引导类，便于用户自定义配置。

| 方法 | 说明 | 默认值 |
|:---|:---|:---|
| rateLimit | 限流策略 | `RateLimits.tokenBucket()` 令牌桶算法 |
| timer | 时间策略 | `Timers.system()` 系统时间 |
| cacheService | 缓存策略 | `CommonCacheServiceMap` 基于本地 map 的缓存策略 |
| configService | 限制配置策略 | `RateLimitConfigService` 默认基于方法上的注解 |
| tokenService | 身份标识策略 | `RateLimitTokenService` 默认基于 IP |
| methodService | 方法标识策略 | `RateLimitMethodService` 默认基于方法名+参数类型 |
| rejectListener | 拒绝策略 | `RateLimitRejectListenerException` 限流时抛出异常 |

其中 rateLimit 内置 `RateLimits` 工具中的策略如下：

| 方法 | 说明 |
|:---|:---|
| fixedWindow() | 固定窗口 |
| slideWindow(int windowNum) | 滑动窗口，可指定窗口大小 |
| slideWindow() | 滑动窗口，默认为 10 |
| slideWindowQueue() | 滑动窗口，基于队列的实现 |
| leakyBucket() | 漏桶算法 |
| tokenBucket() | 令牌桶算法 |

### 配置建议

1. 分布式系统，cacheService 建议使用基于 redis 的集中式缓存策略。

2. configService 如果想更加灵活，可以基于数据库的配置查询

### RateLimitBs 引导类

RateLimitBs 默认配置如下：

```java
RateLimitBs.newInstance()
      .timer(Timers.system())
      .methodService(new RateLimitMethodService())
      .tokenService(new RateLimitTokenService())
      .rejectListener(new RateLimitRejectListenerException())
      .configService(new RateLimitConfigService())
      .cacheService(new CommonCacheServiceMap())
      .rateLimit(RateLimits.tokenBucket());
```

# spring 整合

## maven 引入

```xml
<dependency>
    <groupId>com.github.houbb</groupId>
    <artifactId>rate-limit-spring</artifactId>
    <version>1.0.0</version>
</dependency>
```

## 类定义

### 方法

和上面使用类似，直接在方法上声明 `@RateLimit` 注解即可。

```java
@Service
public class UserService {

    private static final Log log = LogFactory.getLog(UserService.class);

    @RateLimit(interval = 2, count = 5)
    public void limitCount() {
        log.info("{}", Thread.currentThread().getName());
    }

}
```

### 配置

通过 `@EnableRateLimit` 声明启用限流。

```java
@Configuration
@ComponentScan("com.github.houbb.rate.limit.test.core")
@EnableRateLimit
public class SpringConfig {

}
```

`@EnableRateLimit` 的属性配置和 RateLimitBs 属性是以一一对应的。

| 方法 | 说明 | 默认值 |
|:---|:---|:---|
| rateLimit | 限流策略 | 令牌桶算法 |
| timer | 时间策略 | 系统时间 |
| cacheService | 缓存策略 | 基于本地 map 的缓存策略 |
| configService | 限制配置策略 | 默认基于方法上的注解 |
| tokenService | 身份标识策略 | 默认基于 IP |
| methodService | 方法标识策略 | 默认基于方法名+参数类型 |
| rejectListener | 拒绝策略 | 限流时抛出异常 |

这里的属性值，都是对应的 spring bean 名称，支持用户自定义。

# spring-boot 整合

## maven 引入

```xml
<dependency>
    <groupId>com.github.houbb</groupId>
    <artifactId>rate-limit-springboot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

## 使用

其他和 spring 保持一致。

# 后期 Road-MAP

- [ ] `@RateLimit` 类级别 public 方法支持

- [ ] `@RateLimit` 多注解支持

# 缓存相关工具

[cache: 手写渐进式 redis](https://github.com/houbb/cache)

[common-cache: 通用缓存标准定义](https://github.com/houbb/common-cache)

[redis-config: 兼容各种常见的 redis 配置模式](https://github.com/houbb/redis-config)

[lock: 开箱即用的分布式锁](https://github.com/houbb/lock)

[resubmit: 防重复提交](https://github.com/houbb/resubmit)

[rate-limit: 限流](https://github.com/houbb/rate-limit/)
