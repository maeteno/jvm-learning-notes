package com.maeteno.study.java.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Alan.Fu
 */
@Slf4j
public class ReentrantLockExample {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        try {
            log.info("ReentrantLock Demo");
        } finally {
            lock.unlock();
        }
    }
}
