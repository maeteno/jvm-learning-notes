package com.maeteno.study.java.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Alan.Fu
 */
@Slf4j
public class ReentrantReadWriteLockExample {
    static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    static ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
    static ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

    public static void main(String[] args) {
        ReentrantReadWriteLockExample example = new ReentrantReadWriteLockExample();

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                example.write(finalI);
            }, "write-" + finalI).start();
        }

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                example.read(finalI);
            }, "read-" + finalI).start();
        }
    }

    public void write(int i) {
        writeLock.lock();
        try {
            log.info("进入 Write => {}: {}", Thread.currentThread().getName(), i);
            Thread.sleep(1000);
            log.info("退出 Write => {}: {}", Thread.currentThread().getName(), i);
        } catch (InterruptedException e) {
            log.error("InterruptedException", e);
        } finally {
            writeLock.unlock();
        }
    }

    public void read(int i) {
        readLock.lock();
        try {
            log.info("进入 Read => {}: {}", Thread.currentThread().getName(), i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.error("InterruptedException", e);
            }
            log.info("退出 Read => {}: {}", Thread.currentThread().getName(), i);
        } finally {
            readLock.unlock();
        }
    }
}
