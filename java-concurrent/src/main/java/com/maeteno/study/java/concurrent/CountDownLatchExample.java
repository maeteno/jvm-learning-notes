package com.maeteno.study.java.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * 该功能和 go 里面的 sync.WaitGroup 类似
 *
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
                new SynchronousQueue<>()
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

        Future<?> submit = poolExecutor.submit(runnable);
        log.info("submit {}", submit.isDone());
        poolExecutor.execute(runnable);
        poolExecutor.execute(runnable);

        try {
            if (latch.await(3, TimeUnit.SECONDS)) {
                log.info("任务完成");
            } else {
                log.info("任务超时");
            }
            log.info("submit {}", submit.isDone());
            log.info("End...");
        } catch (InterruptedException e) {
            log.info("InterruptedException", e);
            Thread.currentThread().interrupt();
        } finally {
            poolExecutor.shutdown();
        }
    }
}
