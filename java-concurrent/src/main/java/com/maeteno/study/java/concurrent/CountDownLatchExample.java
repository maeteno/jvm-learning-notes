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


    public static void main(String[] args) {
        final ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
                0,
                Integer.MAX_VALUE,
                60L,
                TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>()
        );

        CountDownLatch latch = new CountDownLatch(4);

        Runnable runnable = () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.info("InterruptedException", e);
                Thread.currentThread().interrupt();
            }
            log.info("Running 1 {}", Thread.currentThread().getName());
            latch.countDown();
        };

        poolExecutor.execute(runnable);
        poolExecutor.execute(runnable);
        poolExecutor.execute(runnable);

        try {
            if (latch.await(3, TimeUnit.SECONDS)) {
                log.info("任务完成");
            }else{
                log.info("任务超时");
            }
            log.info("End...");
        } catch (InterruptedException e) {
            log.info("InterruptedException", e);
            Thread.currentThread().interrupt();
        } finally {
            poolExecutor.shutdown();
        }
    }
}
