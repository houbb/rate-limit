package com.github.houbb.rate.limit.core.bs;

import com.github.houbb.heaven.support.instance.impl.Instances;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.rate.limit.core.core.ILimit;
import com.github.houbb.rate.limit.core.core.ILimitContext;
import com.github.houbb.rate.limit.core.core.impl.LimitContext;
import com.github.houbb.rate.limit.core.core.impl.LimitFixedWindow;
import com.github.houbb.rate.limit.core.exception.RateLimitRuntimeException;
import com.github.houbb.rate.limit.core.support.ICurrentTime;
import com.github.houbb.rate.limit.core.support.IIsFirstTime;
import com.github.houbb.rate.limit.core.support.ILimitHandler;
import com.github.houbb.rate.limit.core.support.ITimeDiffer;
import com.github.houbb.rate.limit.core.support.impl.CurrentTime;
import com.github.houbb.rate.limit.core.support.impl.IsFirstTime;
import com.github.houbb.rate.limit.core.support.impl.LimitHandler;
import com.github.houbb.rate.limit.core.support.impl.TimeDiffer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

/**
 * <p> project: rate-acquire-LimitBs </p>
 * <p> create on 2020/6/20 21:38 </p>
 *
 * @author binbin.hou
 * @since 0.0.3
 */
public final class LimitBs {

    private LimitBs(){}

    /**
     * 新建对象实例
     * @return this
     * @since 0.0.1
     */
    public static LimitBs newInstance() {
        return new LimitBs();
    }

    /**
     * 新建对象实例
     * @param limitClass 限制类
     * @return this
     * @since 0.0.5
     */
    public static LimitBs newInstance(final Class<? extends ILimit> limitClass) {
        LimitBs limitBs = new LimitBs();
        limitBs.limit(limitClass);
        return limitBs;
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
    private Class<? extends ILimit> limit = LimitFixedWindow.class;

    /**
     * 设置时间单位
     * @param timeUnit 时间单位
     * @return this
     * @since 0.0.3
     */
    public LimitBs timeUnit(TimeUnit timeUnit) {
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
    public LimitBs interval(long interval) {
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
    public LimitBs count(int count) {
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
    public LimitBs limit(Class<? extends ILimit> limit) {
        ArgUtil.notNull(limit, "acquire");

        this.limit = limit;
        return this;
    }

    /**
     * 构建对象实例
     * @return 实例
     * @since 0.0.3
     */
    public ILimit build() {
        try {
            ILimitContext context = LimitContext.newInstance()
                    .timeUnit(timeUnit)
                    .count(count)
                    .interval(interval)
                    ;

            Constructor constructor = this.limit.getConstructor(ILimitContext.class);
            return (ILimit) constructor.newInstance(context);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RateLimitRuntimeException(e);
        }
    }

}
