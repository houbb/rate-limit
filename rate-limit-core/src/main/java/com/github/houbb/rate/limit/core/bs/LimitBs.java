package com.github.houbb.rate.limit.core.bs;

import com.github.houbb.heaven.support.instance.impl.Instances;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.rate.limit.core.core.ILimit;
import com.github.houbb.rate.limit.core.core.ILimitContext;
import com.github.houbb.rate.limit.core.core.impl.LimitContext;
import com.github.houbb.rate.limit.core.core.impl.LimitFrequencyFixedWindow;
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
 * <p> project: rate-limit-LimitBs </p>
 * <p> create on 2020/6/20 21:38 </p>
 *
 * @author binbin.hou
 * @since 0.0.3
 */
public final class LimitBs {

    private LimitBs(){}

    public static LimitBs newInstance() {
        return new LimitBs();
    }

    /**
     * 当前时间
     * @since 0.0.3
     */
    private ICurrentTime currentTime = Instances.singleton(CurrentTime.class);

    /**
     * 超时限制的处理者
     * @since 0.0.3
     */
    private ILimitHandler limitHandler = Instances.singleton(LimitHandler.class);

    /**
     * 是否为第一次
     * @since 0.0.3
     */
    private IIsFirstTime isFirstTime = Instances.singleton(IsFirstTime.class);

    /**
     * 时间差异
     * @since 0.0.3
     */
    private ITimeDiffer timeDiffer = Instances.singleton(TimeDiffer.class);

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
    private Class<? extends ILimit> limit = LimitFrequencyFixedWindow.class;

    private LimitBs currentTime(ICurrentTime currentTime) {
        this.currentTime = currentTime;
        return this;
    }

    private LimitBs limitHandler(ILimitHandler limitHandler) {
        this.limitHandler = limitHandler;
        return this;
    }

    private LimitBs isFirstTime(IIsFirstTime isFirstTime) {
        this.isFirstTime = isFirstTime;
        return this;
    }

    private LimitBs timeDiffer(ITimeDiffer timeDiffer) {
        this.timeDiffer = timeDiffer;
        return this;
    }

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
        ArgUtil.notNull(limit, "limit");

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
                    .currentTime(currentTime)
                    .interval(interval)
                    .isFirstTime(isFirstTime)
                    .limitHandler(limitHandler)
                    .timeDiffer(timeDiffer)
                    ;

            Constructor constructor = this.limit.getConstructor(ILimitContext.class);
            return (ILimit) constructor.newInstance(context);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RateLimitRuntimeException(e);
        }
    }

}
