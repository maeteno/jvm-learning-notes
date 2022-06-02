package com.maeteno.study.java.concurrent.stream;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
public class StreamDemo03 {
    public static void main(String[] args) {
        Iterable<Integer> iterable = new MyIterable();
        ForkJoinPool forkJoinPool = new ForkJoinPool(10);

        List<Integer> collect = StreamSupport.stream(iterable.spliterator(), true).collect(Collectors.toList());

        ForkJoinTask<?> joinTask = forkJoinPool.submit(() -> {
            collect.stream()
                    .parallel()
                    .map(it -> {
                        log.info("forkJoinPool map: {} <== {}", it, Thread.currentThread().getName());
                        if (it % 3 == 0) {
                            try {
                                Thread.sleep(10000L);
                            } catch (Exception e) {
                                log.warn("Exception: {}", e.getMessage(), e);
                            }
                        }
                        return it.toString();
                    })
                    .forEach(it -> log.info("for each: {} <== {}", it, Thread.currentThread().getName()));
        });

        joinTask.join();
        log.info("getPoolSize: {}",forkJoinPool.getPoolSize());
    }

}
