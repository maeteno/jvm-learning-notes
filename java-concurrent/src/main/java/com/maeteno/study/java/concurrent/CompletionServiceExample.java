package com.maeteno.study.java.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.*;

/**
 * @author Warlock-Team
 */
@Slf4j
public class CompletionServiceExample {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        CompletionService<Integer> completionService = new ExecutorCompletionService<>(threadPool);

        for (int i = 0; i < 100; i++) {
            final int finalI = i;
            completionService.submit(() -> {
                TimeUnit.SECONDS.sleep(1L);
                return finalI;
            });
        }

        /*
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
         */

        /*
        while (true) {
            try {
                Future<Integer> result = completionService.take();
                Integer resultCode = result.get();
                log.info("take: {}", resultCode);
                if (resultCode < 1) {
                    break;
                }
            } catch (InterruptedException e) {
                log.error("InterruptedException: {}", e.getMessage(), e);
                Thread.currentThread().interrupt();
            } catch (ExecutionException e) {
                log.error("ExecutionException: {}", e.getMessage(), e);
            }
        }
        */

        while (true) {
            try {
                Future<Integer> result = completionService.poll(15, TimeUnit.SECONDS);

                if (Objects.isNull(result)) {
                    log.info("poll timeout");
                    break;
                }

                Integer resultCode = result.get();
                log.info("poll: {}", resultCode);
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
