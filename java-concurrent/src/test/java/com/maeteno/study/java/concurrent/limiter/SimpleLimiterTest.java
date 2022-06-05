package com.maeteno.study.java.concurrent.limiter;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
class SimpleLimiterTest {

    @Test
    void testSimpleLimiter() {
        final SimpleLimiter simpleLimiter = new SimpleLimiter();
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 20; i++) {
            executorService.submit(() -> {
                simpleLimiter.acquire();
                System.out.println(Thread.currentThread().getName());
            });
        }
    }
}