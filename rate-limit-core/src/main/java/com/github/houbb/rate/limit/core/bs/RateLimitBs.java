package com.github.houbb.rate.limit.core.bs;

import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.rate.limit.core.core.IRateLimit;
import com.github.houbb.rate.limit.core.core.IRateLimitContext;
import com.github.houbb.rate.limit.core.core.impl.RateLimitContext;
import com.github.houbb.rate.limit.core.core.impl.RateLimitFixedWindow;
import com.github.houbb.rate.limit.core.exception.RateLimitRuntimeException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

/**
 * <p> project: rate-acquire-RateLimitBs </p>
 * <p> create on 2020/6/20 21:38 </p>
 *
 * @author binbin.hou
 * @since 0.0.3
 */
public final class RateLimitBs {

    private RateLimitBs(){}

    /**
     * 新建对象实例
     * @return this
     * @since 0.0.1
     */
    public static RateLimitBs newInstance() {
        return new RateLimitBs();
    }

    /**
     * 新建对象实例
     * @param limitClass 限制类
     * @return this
     * @since 0.0.5
     */
    public static RateLimitBs newInstance(final Class<? extends IRateLimit> limitClass) {
        RateLimitBs rateLimitBs = new RateLimitBs();
        rateLimitBs.limitClass(limitClass);
        return rateLimitBs;
    }

    /**
     * 时间单位, 默认为秒
     * @see TimeUnit 时间单位
     * @since 0.0.1
     */
    private TimeUnit timeUnit = TimeUnit.SECONDS;

    /**
     * 时间间隔
     * (1) 需要填入正整数。
     * @since 0.0.1
     */
    private long interval = 1;

    /**
     * 调用次数。
     * (1) 需要填入正整数。
     * @since 0.0.3
     */
    private int count = 100;

    /**
     * 限制策略
     * @since 0.0.3
     */
    private Class<? extends IRateLimit> limit = RateLimitFixedWindow.class;

    /**
     * 设置时间单位
     * @param timeUnit 时间单位
     * @return this
     * @since 0.0.3
     */
    public RateLimitBs timeUnit(TimeUnit timeUnit) {
        ArgUtil.notNull(timeUnit, "timeUnit");

        this.timeUnit = timeUnit;
        return this;
    }

    /**
     * 时间间隔
     * @param interval 时间间隔
     * @return this
     * @since 0.0.3
     */
    public RateLimitBs interval(long interval) {
        ArgUtil.gte("interval", interval, 1);

        this.interval = interval;
        return this;
    }

    /**
     * 次数
     * @param count 次数
     * @return this
     * @since 0.0.3
     */
    public RateLimitBs count(int count) {
        ArgUtil.gte("count", count, 1);

        this.count = count;
        return this;
    }

    /**
     * 设置实现策略
     * @param limit 限制
     * @return this
     * @since 0.0.3
     */
    public RateLimitBs limitClass(Class<? extends IRateLimit> limit) {
        ArgUtil.notNull(limit, "acquire");

        this.limit = limit;
        return this;
    }

    /**
     * 构建对象实例
     * @return 实例
     * @since 0.0.3
     */
    public IRateLimit build() {
        try {
            IRateLimitContext context = RateLimitContext.newInstance()
                    .timeUnit(timeUnit)
                    .count(count)
                    .interval(interval)
                    ;

            Constructor constructor = this.limit.getConstructor(IRateLimitContext.class);
            return (IRateLimit) constructor.newInstance(context);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RateLimitRuntimeException(e);
        }
    }

}
