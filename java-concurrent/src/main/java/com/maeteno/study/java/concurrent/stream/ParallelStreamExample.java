package com.maeteno.study.java.concurrent.stream;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

/**
 * @author Warlock-Team
 */
@Slf4j
public class ParallelStreamExample {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add(i);
        }

        list.stream()
                .parallel()
                .map(it -> {
                    log.info("map: {} => {}", it, Thread.currentThread().getName());
                    try {
                        TimeUnit.SECONDS.sleep(1L);
                    } catch (InterruptedException e) {
                        log.error("InterruptedException: {}", e.getMessage(), e);
                        Thread.currentThread().interrupt();
                    }
                    return it;
                })
                .forEach(it -> log.info("each: {} => {}", it, Thread.currentThread().getName()));

        log.info("commonPool: {}", ForkJoinPool.commonPool().getPoolSize());
    }
}
