package com.github.houbb.rate.limit.core.util;

import com.github.houbb.heaven.annotation.CommonEager;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.util.ArrayUtil;

import java.util.concurrent.*;

/**
 * <p> project: rate-acquire-ThreadUtil </p>
 * <p> create on 2020/6/21 12:08 </p>
 *
 * @author binbin.hou
 * @since 0.0.4
 */
@CommonEager
public final class ExecutorServiceUtil {

    private ExecutorServiceUtil(){}

    /**
     * 单个定时线程的实现
     *
     * 1、scheduleAtFixedRate 方法，顾名思义，它的方法名称的意思是：已固定的频率来执行某项计划(任务)。
     * 2、scheduleWithFixedDelay,相对固定的延迟后，执行某项计划。
     *
     * 还是比较简单明了的描述比较好：第一个方法是固定的频率来执行某项计划，它不受计划执行时间的影响。到时间，它就执行。
     * 而第二个方法，相对固定，据鄙人理解，是相对任务的。即无论某个任务执行多长时间，等执行完了，我再延迟指定的时间。也就是第二个方法，它受计划执行时间的影响。
     *
     * @param runnable job
     * @param interval 时间间隔
     * @param timeUnit 时间单位
     *
     * @since 0.0.4
     */
    public static void singleSchedule(final Runnable runnable,
                                      final long interval,
                                      final TimeUnit timeUnit) {
        singleSchedule(runnable, 0, interval, timeUnit, null);
    }


    /**
     * 当进程的任务调度
     * @param runnable 执行任务
     * @param delay 延迟
     * @param interval 间隔
     * @param timeUnit 时间
     * @param threadName 线程名
     * @since 0.0.4
     */
    public static void singleSchedule(final Runnable runnable,
                                      final long delay,
                                      final long interval,
                                      final TimeUnit timeUnit,
                                      final String... threadName) {
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                if(ArrayUtil.isNotEmpty(threadName)) {
                    thread.setName(threadName[0]);
                }

                thread.setDaemon(true);

                return thread;
            }
        };

        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1, threadFactory,
                new ThreadPoolExecutor.DiscardPolicy());
        executor.scheduleAtFixedRate(runnable, delay, interval, timeUnit);
    }


    @Deprecated
    public static Thread thread(final Runnable runnable,
                                final boolean isDaemon,
                                final String threadName
                                ) {
        Thread thread = new Thread(runnable);
        if(StringUtil.isNotEmpty(threadName)) {
            thread.setName(threadName);
        }

        thread.setDaemon(isDaemon);
        return thread;
    }

    public static Thread thread(final Runnable runnable) {
        return thread(runnable, false, "");
    }
}
