package com.maeteno.study.java.concurrent.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class PrintABCByLock {

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        ReentrantLock lock = new ReentrantLock();

        Condition c1 = lock.newCondition();
        Condition c2 = lock.newCondition();
        Condition c3 = lock.newCondition();

        AtomicInteger count = new AtomicInteger(0);

        threadPool.submit(() -> {
            lock.lock();
            try {
                // 先等到一会，反正后面的线程还没启动
                while (count.get() < 2) {
                    c1.await(100, TimeUnit.MICROSECONDS);
                }

                for (int i = 0; i < 10; i++) {
                    System.out.print("A");
                    c2.signal();
                    c1.await();
                }
                c2.signal();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        });

        threadPool.submit(() -> {
            lock.lock();
            try {
                count.incrementAndGet();
                c2.await();
                for (int i = 0; i < 10; i++) {
                    System.out.print("B");
                    c3.signal();
                    c2.await();
                }
                c3.signal();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        });

        threadPool.submit(() -> {
            lock.lock();
            try {
                count.incrementAndGet();
                c3.await();
                for (int i = 0; i < 10; i++) {
                    System.out.print("C");
                    c1.signal();
                    c3.await();
                }
                c1.signal();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        });

        threadPool.shutdown();
    }
}
