package com.github.houbb.rate.limit.test.core.leaky;

import com.github.houbb.heaven.util.util.TimeUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.rate.limit.core.bs.LimitBs;
import com.github.houbb.rate.limit.core.core.ILimit;
import com.github.houbb.rate.limit.core.core.impl.LimitLeakyBucket;
import com.github.houbb.rate.limit.core.core.impl.LimitTokenBucket;

/**
 * <p> project: rate-limit-LimitTokenBucketTest </p>
 * <p> create on 2020/6/22 22:38 </p>
 *
 * @author binbin.hou
 * @since 0.0.7
 */
public class LimitLeakyBucketTest {

    private static final Log LOG = LogFactory.getLog(LimitTokenBucket.class);

    /**
     * 2S 内最多运行 5 次
     * @since 0.0.5
     */
    private static final ILimit LIMIT = LimitBs.newInstance()
            .interval(1)
            .count(5)
            .limit(LimitLeakyBucket.class)
            .build();

    static class LimitRunnable implements Runnable {
        @Override
        public void run() {
            for(int i = 0; i < 6; i++) {
                while (!LIMIT.acquire()) {
                    // 等待令牌
                    TimeUtil.sleep(100);
                }

                LOG.info("{}-{}", Thread.currentThread().getName(), i);
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new LimitRunnable()).start();
    }

}
