package com.maeteno.study.java.concurrent.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Alan.Fu
 */
@Slf4j
public class BaseAtomicExample {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        AtomicInteger i = new AtomicInteger(0);

        for (int j = 0; j < 100; j++) {
            pool.execute(() -> i.incrementAndGet());
        }

        pool.shutdown();
        if (pool.awaitTermination(1, TimeUnit.SECONDS)) {
            log.info("{}", i.get());
        }
    }
}
