package com.github.houbb.rate.limit.spring.support.beans;

import com.github.houbb.rate.limit.core.core.ILimit;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 这里可以提前初始化好所需要的 limiter 实现类。
 *
 * 但是好像没有必要。
 * <p> project: rate-limit-BeanLimit </p>
 * <p> create on 2020/6/20 20:33 </p>
 *
 * https://blog.csdn.net/xiaojin21cen/article/details/83418470
 *
 * @author binbin.hou
 * @since 0.0.3
 */
//@Component
public class BeanLimitMethodInit implements InitializingBean, ApplicationListener<ContextRefreshedEvent> {

    /**
     * 用来存放方法的限制器
     * Key=方法全名+注解名称
     * @since 0.0.3
     */
    private Map<String, ILimit> limitHashMap = new ConcurrentHashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        // 所有属性加载完成
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        contextRefreshedEvent.getApplicationContext().getBeansWithAnnotation();
    }


}
