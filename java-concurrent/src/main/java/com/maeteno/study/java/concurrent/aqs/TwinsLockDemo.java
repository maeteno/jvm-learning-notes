package com.maeteno.study.java.concurrent.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Lock;

/**
 * @author Alan.Fu
 */
@Slf4j
public class TwinsLockDemo {
    public static void main(String[] args) {
        Lock lock = new TwinsLock();

        for (int i = 0; i < 100; i++) {
            final int finalI = i;
            new Thread(() -> {
                lock.lock();
                try {
                    Thread.sleep(1000L);
                    log.info("Lock T" + finalI);
                } catch (InterruptedException e) {
                    log.error("InterruptedException", e);
                } finally {
                    lock.unlock();
                    log.info("Unlock T" + finalI);

                }
            }, "T-" + finalI).start();
        }
    }
}
