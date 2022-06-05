package com.maeteno.study.java.concurrent.stream;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Slf4j
public class StreamDemo02 {
    public static void main(String[] args) {
        Iterable<Integer> iterable = new MyIterable();
        ForkJoinPool forkJoinPool = new ForkJoinPool(10);
        log.info("getPoolSize: {}", forkJoinPool.getPoolSize());
        Stream<Integer> stream = StreamSupport.stream(iterable.spliterator(), true);

        ForkJoinTask<?> joinTask = forkJoinPool.submit(() -> stream.parallel()
                .map(it -> {
                    log.info("forkJoinPool map: {} <== {}", it, Thread.currentThread().getName());

                    try {
                        TimeUnit.SECONDS.sleep(1L);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        log.error("{}", e.getMessage(), e);
                    }

                    return it.toString();
                })
                .forEach(it -> log.info("for each: {} <== {}", it, Thread.currentThread().getName())));

        joinTask.join();
        log.info("getPoolSize: {}", forkJoinPool.getPoolSize());
    }

}
