package com.maeteno.study.java.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * @author Alan.Fu
 */
@Slf4j
public class ThreadWait {
    public static void main(String[] args) {
        Object lock = new Object();

        new Thread(() -> {
            synchronized (lock) {
                Thread that = Thread.currentThread();
                LockSupport.park();
                LockSupport.unpark(that);

                try {
                    log.info("Start wait");
                    lock.wait();
                    log.info("End wait");
                } catch (InterruptedException e) {
                    log.error("InterruptedException:", e);
                }
            }
        }, "Thread-0").start();

        new Thread(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                log.error("InterruptedException:", e);
            }

            synchronized (lock) {
                log.info("Start notify");
                lock.notify();
                log.info("End notify");
            }
        }, "Thread-1").start();
    }
}
