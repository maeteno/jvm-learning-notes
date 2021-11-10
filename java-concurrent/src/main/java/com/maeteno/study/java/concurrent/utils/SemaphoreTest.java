package com.maeteno.study.java.concurrent.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author Alan.Fu
 */
@Slf4j
public class SemaphoreTest {
    private static final int THREAD_COUNT = 10;
    private static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(THREAD_COUNT);
    private static final Semaphore SEMAPHORE = new Semaphore(2);

    public static void main(String[] args) {
        for (int i = 0; i < THREAD_COUNT; i++) {
            THREAD_POOL.execute(() -> {
                try {
                    long id = Thread.currentThread().getId();
                    log.info("start: {}", id);
                    SEMAPHORE.acquire();
                    log.info("save data: {}", id);
                    Thread.sleep(1000L);
                    log.info("end: {}", id);
                    SEMAPHORE.release();
                } catch (InterruptedException e) {
                    log.error("InterruptedException", e);
                    Thread.currentThread().interrupt();
                }
            });
        }

        THREAD_POOL.shutdown();
    }
}
