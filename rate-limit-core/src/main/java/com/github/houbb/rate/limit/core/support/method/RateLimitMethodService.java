package com.github.houbb.rate.limit.core.support.method;

import com.github.houbb.heaven.annotation.CommonEager;
import com.github.houbb.heaven.constant.PunctuationConst;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.rate.limit.api.support.IRateLimitMethodService;

import java.lang.reflect.Method;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
public class RateLimitMethodService implements IRateLimitMethodService {

    @Override
    public String getMethodId(Method method, Object[] params) {
        // 是否需要包含 class?
        return getMethodFullName(method);
    }

    /**
     * 方法全名此处应该考虑不同的参数问题。
     *
     * @param method 方法
     * @return 完整的方法名称
     * @since 0.0.1
     */
    @CommonEager
    private static String getMethodFullName(Method method) {
        if(method == null) {
            return "null";
        }

        final String className = method.getDeclaringClass().getName();
        Class[] types = method.getParameterTypes();
        StringBuilder nameBuilder = new StringBuilder(className + "." + method.getName());
        if (ObjectUtil.isNotEmpty(types)) {
            for (Class parameter : types) {
                nameBuilder.append(PunctuationConst.COLON).append(parameter.getName());
            }
        }
        return nameBuilder.toString();
    }

}
