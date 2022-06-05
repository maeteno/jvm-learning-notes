package com.maeteno.study.java.concurrent;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class RateLimiterExample {
    public static void main(String[] args) {
        //限流器流速：1个请求/2秒
        var limiter = RateLimiter.create(0.8);

        //执行任务的线程池
        ExecutorService es = Executors.newFixedThreadPool(10);

        //记录上一次执行时间
        AtomicLong prev = new AtomicLong(System.nanoTime());

        for (int i = 0; i < 10; i++) {
            //限流器限流
            limiter.acquire();
            //提交任务异步执行
            es.execute(() -> {
                long cur = System.nanoTime();
                //打印时间间隔：毫秒
                log.info("间隔： {}", (cur - prev.get()) / 1000_000);
                prev.set(cur);
            });
        }

        es.shutdown();
    }
}
