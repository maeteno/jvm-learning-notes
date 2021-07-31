package com.maeteno.study.java.concurrent.thread;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

@Slf4j
public class FutureTaskThreadExample {

    @SneakyThrows
    public void callableDemo() {
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 100;
            }
        };

        FutureTask<Integer> task = new FutureTask<>(callable);
        Thread thread = new Thread(task, "future-task");

        thread.start();

        Integer r = task.get();

        log.info("r:{}", r);
    }

}
