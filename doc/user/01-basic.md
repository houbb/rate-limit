
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
| 1 | `GlobalLimitCount` | [GlobalLimitCountTest](https://github.com/houbb/rate-limit/blob/master/https://github.com/houbb/rate-limit/blob/master/rate-limit-test/src/test/java/com/github/houbb/rate/limit/test/core/GlobalLimitCountTest.java)|
| 2 | `ThreadLocalLimitCount` | [ThreadLocalLimitCountTest](https://github.com/houbb/rate-limit/blob/master/rate-limit-test/src/test/java/com/github/houbb/rate/limit/test/core/ThreadLocalLimitCountTest.java)|
| 3 | `GlobalLimitFrequency` | [GlobalLimitFrequencyTest](https://github.com/houbb/rate-limit/blob/master/rate-limit-test/src/test/java/com/github/houbb/rate/limit/test/core/GlobalLimitFrequencyTest.java)|
| 4 | `ThreadLocalLimitFrequency` | [ThreadLocalLimitFrequencyTest](https://github.com/houbb/rate-limit/blob/master/rate-limit-test/src/test/java/com/github/houbb/rate/limit/test/core/ThreadLocalLimitFrequencyTest.java)|
