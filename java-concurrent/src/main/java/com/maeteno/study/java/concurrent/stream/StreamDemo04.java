package com.maeteno.study.java.concurrent.stream;

import lombok.extern.slf4j.Slf4j;

import java.util.Spliterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.StreamSupport;

@Slf4j
public class StreamDemo04 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Iterable<Integer> iterable = new MyIterable();
        Spliterator<Integer> spliterator = iterable.spliterator();

        StreamSupport.stream(spliterator, false)
                .map(it -> {
                    log.info("map: {} <== {}", it, Thread.currentThread().getName());
                    return it.toString();
                })
                .forEach(it -> executorService.submit(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(1L);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        log.error("{}", e.getMessage(), e);
                    }

                    log.info("for each: {} <== {}", it, Thread.currentThread().getName());
                }));

        executorService.shutdown();
    }
}
