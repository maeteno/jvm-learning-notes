package com.maeteno.study.java.concurrent.stream;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
public class StreamDemo03 {
    public static void main(String[] args) {
        Iterable<Integer> iterable = new MyIterable();
        ForkJoinPool forkJoinPool = new ForkJoinPool(15);

        List<Integer> collect = StreamSupport.stream(iterable.spliterator(), true)
                .collect(Collectors.toList());

        ForkJoinTask<?> joinTask = forkJoinPool.submit(() -> collect.stream()
                .parallel()
                .map(it -> {
                    log.info("forkJoinPool A map: {} <== {}", it, Thread.currentThread().getName());

                    try {
                        TimeUnit.SECONDS.sleep(1L);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        log.error("InterruptedException: {}", e.getMessage(), e);
                    }

                    return it.toString();
                })
                .forEach(it -> log.info("for each: {} <== {}", it, Thread.currentThread().getName())));

        joinTask.join();
        log.info("getPoolSize: {}", forkJoinPool.getPoolSize());
    }

}
