package com.maeteno.study.java.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * 循环屏障
 * <p>
 * 可用于协调多个任务之前的协同。
 * CountDownLatch是用于等待多个任务完成，然后汇总。不会阻塞子任务。而CyclicBarrier会阻塞子任务等待一个时机的完成，然后协同执行。
 *
 * @author Alan.Fu
 */
@Slf4j
public class CyclicBarrierExample {
    public static void main(String[] args) {
        final ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
                2,
                2,
                60L,
                TimeUnit.SECONDS,
                new SynchronousQueue<>()
        );

        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () -> log.info("ALL END"));

        poolExecutor.execute(() -> {
            try {
                log.info("Start 1");
                cyclicBarrier.await();
                log.info("End 1");
            } catch (InterruptedException | BrokenBarrierException e) {
                log.error("Exception: ", e);
            }
        });

        poolExecutor.execute(() -> {
            try {
                log.info("Start 2");
                Thread.sleep(2000L);
                cyclicBarrier.await();
                log.info("End 2");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });


        poolExecutor.shutdown();
    }
}
