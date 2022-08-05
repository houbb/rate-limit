package com.github.houbb.rate.limit.core.support.token;

import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.util.ArrayUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.rate.limit.api.support.IRateLimitTokenService;
import com.github.houbb.web.core.util.HttpServletRequestUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 默认基于 ip 获取对应的地址
 *
 * @author binbin.hou
 * @since 1.0.0
 */
public class RateLimitTokenService implements IRateLimitTokenService {

    /**
     * 日志信息
     * @since 0.0.1
     */
    private static final Log LOG = LogFactory.getLog(RateLimitTokenService.class);

    @Override
    public String getTokenId(Object[] params) {
        if(ArrayUtil.isEmpty(params)) {
            LOG.warn("Param is empty, return empty");
            return StringUtil.EMPTY;
        }

        for(Object param : params) {
            if(param instanceof HttpServletRequest) {
                HttpServletRequest request = (HttpServletRequest) param;
                return HttpServletRequestUtil.getIp(request);
            }
        }
        LOG.warn("Param is not found in request, return empty");
        return StringUtil.EMPTY;
    }

}
