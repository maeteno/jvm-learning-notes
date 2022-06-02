package com.maeteno.study.java.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author Warlock-Team
 */
@Slf4j
public class CompletionServiceExample {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        CompletionService<Integer> completionService = new ExecutorCompletionService<>(threadPool);

        for (int i = 0; i < 10; i++) {
            final int finalI = i;
            completionService.submit(() -> {
                TimeUnit.SECONDS.sleep(12 - finalI);
                return finalI;
            });
        }

        for (int i = 0; i < 10; i++) {
            try {
                Future<Integer> result = completionService.take();
                log.info("take: {}", result.get());
            } catch (InterruptedException e) {
                log.error("InterruptedException: {}", e.getMessage(), e);
                Thread.currentThread().interrupt();
            } catch (ExecutionException e) {
                log.error("ExecutionException: {}", e.getMessage(), e);
            }
        }

        threadPool.shutdown();
    }
}
