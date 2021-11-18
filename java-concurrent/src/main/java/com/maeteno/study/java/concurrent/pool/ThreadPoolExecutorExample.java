package com.maeteno.study.java.concurrent.pool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executor;


/**
 * @author Alan.Fu
 */
@Slf4j
public class ThreadPoolExecutorExample {
    public static void main(String[] args) {
        Executor executor = new MyThreadPoolExecutor(5);

        for (int i = 0; i < 1000; i++) {
            final int finalI = i;
            executor.execute(() -> {
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("Thread Name: {} => {}", Thread.currentThread().getName(), finalI);
            });
        }
    }
}
