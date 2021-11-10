package com.maeteno.study.java.concurrent.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @author Alan.Fu
 */
@Slf4j
public class ArrayAtomicExample {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        AtomicIntegerArray i = new AtomicIntegerArray(new int[]{0, 0, 0, 0, 0});

        for (int j = 0; j < 100; j++) {
            final int finalJ = j;
            pool.execute(() -> i.incrementAndGet(finalJ % 5));
        }

        pool.shutdown();
        if (pool.awaitTermination(1, TimeUnit.SECONDS)) {
            log.info("{}", i.get(0));
            log.info("{}", i.get(1));
            log.info("{}", i.get(2));
            log.info("{}", i.get(3));
            log.info("{}", i.get(4));
        }
    }
}
