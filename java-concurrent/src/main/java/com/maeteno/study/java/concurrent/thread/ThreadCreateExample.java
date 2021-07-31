package com.maeteno.study.java.concurrent.thread;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadCreateExample {
    public void createByThread() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                log.info("createByThread");
            }
        };

        thread.start();
    }

    public void createByRunnable() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                log.info("createByRunnable");
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

    public void createByLambda() {
        Runnable runnable = () -> log.info("createByLambda");

        Thread thread = new Thread(runnable);
        thread.start();
    }
}
