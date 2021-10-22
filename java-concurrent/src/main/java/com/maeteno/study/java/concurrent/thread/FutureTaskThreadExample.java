package com.maeteno.study.java.concurrent.thread;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

/**
 * @author Alan.Fu
 */
@Slf4j
public class FutureTaskThreadExample {

    public static void main(String[] args) {
        FutureTaskThreadExample example = new FutureTaskThreadExample();
        example.callableDemo();
    }

    @SneakyThrows
    public void callableDemo() {
        Callable<Integer> callable = () -> 100;
        RunnableFuture<Integer> task = new FutureTask<>(callable);

        Thread thread = new Thread(task, "future-task");

        thread.start();

        Integer r = task.get();
        log.info("r:{}", r);
    }
}
