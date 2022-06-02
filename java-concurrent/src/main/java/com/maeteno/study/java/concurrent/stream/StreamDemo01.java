package com.maeteno.study.java.concurrent.stream;

import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.StreamSupport;

@Slf4j
public class StreamDemo01 {
    public static void main(String[] args) {
        Iterable<Integer> iterable = new MyIterable();

        StreamSupport.stream(iterable.spliterator(), true)
                .parallel()
                .map(it -> {
                    log.info("map: {} <== {}", it, Thread.currentThread().getName());
                    if (it % 5 == 0) {
                        try {
                            Thread.sleep(1000L);
                        } catch (Exception e) {
                            log.warn("Exception: {}", e.getMessage(), e);
                        }
                    }
                    return it.toString();
                })
                .forEach(it -> log.info("for each: {} <== {}", it, Thread.currentThread().getName()));

        int poolSize2 = ForkJoinPool.commonPool().getPoolSize();
        log.info("Pool Size: {}", poolSize2);
    }
}
