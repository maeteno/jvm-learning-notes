package com.maeteno.study.java.concurrent.limiter;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Slf4j
class SimpleLimiter {
    /**
     * 当前令牌桶中的令牌数量
     */
    private long storedPermits = 0;
    /**
     * 下一令牌产生时间
     */
    private long next = System.nanoTime();
    /**
     * 发放令牌间隔：纳秒
     */
    private final long interval = 1000_000_000;

    /**
     * 预占令牌，返回能够获取令牌的时间
     */
    public synchronized long reserve(long now) {
        reSync(now);

        //能够获取令牌的时间
        long at = next;
        //令牌桶中能提供的令牌
        long fb = min(1, storedPermits);
        //令牌净需求：首先减掉令牌桶中的令牌
        long nr = 1 - fb;
        //重新计算下一令牌产生时间
        next = next + nr * interval;
        //重新计算令牌桶中的令牌
        this.storedPermits -= fb;
        return at;
    }

    /**
     * 申请令牌
     */
    public void acquire() {
        //申请令牌时的时间
        long now = System.nanoTime();
        //预占令牌
        long at = reserve(now);
        long waitTime = max(at - now, 0);
        //按照条件等待
        if (waitTime > 0) {
            try {
                TimeUnit.NANOSECONDS.sleep(waitTime);
            } catch (InterruptedException e) {
                log.warn("InterruptedException {}", e.getMessage(), e);
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * 请求时间在下一令牌产生时间之后,则
     * 1.重新计算令牌桶中的令牌数
     * 2.将下一个令牌发放时间重置为当前时间
     */
    private void reSync(long now) {
        if (now > next) {
            //新产生的令牌数
            long newPermits = (now - next) / interval;
            //新令牌增加到令牌桶
            // 令牌桶的容量
            long maxPermits = 3;
            storedPermits = min(maxPermits, storedPermits + newPermits);
            //将下一个令牌发放时间重置为当前时间
            next = now;
        }
    }
}