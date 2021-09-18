package com.maeteno.study.java.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Alan.Fu
 */
@Slf4j
public class CountDownLatchExample {
    private static final ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(
            0,
            Integer.MAX_VALUE,
            60L,
            TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>()
    );

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(4);

        POOL_EXECUTOR.execute(() -> {
            log.info("Running 1 {}", Thread.currentThread().getName());
            latch.countDown();
        });

        POOL_EXECUTOR.execute(() -> {
            log.info("Running 2 {}", Thread.currentThread().getName());
            latch.countDown();
        });

        POOL_EXECUTOR.execute(() -> {
            log.info("Running 3 {}", Thread.currentThread().getName());
            latch.countDown();
        });

        try {
            if (!latch.await(5, TimeUnit.SECONDS)) {
                log.info("等等超时");
            }

            log.info("End...");
        } catch (InterruptedException e) {
            log.info("InterruptedException", e);
        }

        POOL_EXECUTOR.shutdown();
    }
}
