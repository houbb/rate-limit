package com.github.houbb.rate.limit.core.support.proxy.cglib;

import com.github.houbb.rate.limit.core.bs.RateLimitBs;
import com.github.houbb.rate.limit.core.support.proxy.AbstractProxy;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * CGLIB 代理类
 * @author binbin.hou
 * date 2019/3/7
 * @since 0.0.2
 */
public class CglibProxy extends AbstractProxy implements MethodInterceptor {

    /**
     * 被代理的对象
     */
    private final Object target;

    public CglibProxy(Object target) {
        this.target = target;
    }

    public CglibProxy(Object target, RateLimitBs bs) {
        super(bs);
        this.target = target;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        //1. 添加判断
        super.rateLimitBs.tryAcquire(method, objects);

        //2. 返回结果
        return method.invoke(target, objects);
    }

    @Override
    public Object proxy() {
        Enhancer enhancer = new Enhancer();
        //目标对象类
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        //通过字节码技术创建目标对象类的子类实例作为代理
        return enhancer.create();
    }

}
