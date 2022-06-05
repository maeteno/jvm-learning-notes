package com.maeteno.study.java.concurrent.stream;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
public class StreamDemo01 {
    public static void main(String[] args) {
        Iterable<Integer> iterable = new MyIterable();
        Spliterator<Integer> spliterator = iterable.spliterator();

        while (true) {
            List<Integer> collect = StreamSupport.stream(spliterator, false)
                    .limit(9)
                    .collect(Collectors.toList());

            if (collect.isEmpty()) {
                break;
            }

            log.info("Size: {}", collect.size());
            collect.stream()
                    .parallel()
                    .map(it -> {
                        log.info("map: {} <== {}", it, Thread.currentThread().getName());
                        try {
                            TimeUnit.SECONDS.sleep(1L);
                        } catch (InterruptedException e) {
                            log.error(" {}", e.getMessage(), e);
                            Thread.currentThread().interrupt();
                        }
                        return it.toString();
                    })
                    .forEach(it -> log.info("for each: {} <== {}", it, Thread.currentThread().getName()));
        }

        int poolSize2 = ForkJoinPool.commonPool().getPoolSize();
        log.info("Pool Size: {}", poolSize2);
    }
}
