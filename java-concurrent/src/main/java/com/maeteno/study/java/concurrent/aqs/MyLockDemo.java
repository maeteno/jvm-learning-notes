package com.maeteno.study.java.concurrent.aqs;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Alan.Fu
 */
@Slf4j
public class MyLockDemo {
    public static void main(String[] args) {
        MyLock lock = new MyLock();

        new Thread(() -> {
            lock.lock();
            try {
                Thread.sleep(1000L);
                log.info("Lock T1");
            } catch (InterruptedException e) {
                log.error("InterruptedException", e);
            } finally {
                log.info("Unlock T1");
                lock.unlock();
            }
        }, "T1").start();

        new Thread(() -> {
            lock.lock();
            try {
                Thread.sleep(1000L);
                log.info("Lock T2");
            } catch (InterruptedException e) {
                log.error("InterruptedException", e);
            } finally {
                log.info("Unlock T2");
                lock.unlock();
            }
        }, "T2").start();
    }
}
