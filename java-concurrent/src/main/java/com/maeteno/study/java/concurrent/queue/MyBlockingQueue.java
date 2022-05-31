package com.maeteno.study.java.concurrent.queue;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author SL.F
 */
@Slf4j
public class MyBlockingQueue {
    private final Lock lock;
    private final Condition put;
    private final Condition poll;

    private final List<Integer> queue;

    private final Integer size;

    public MyBlockingQueue() {
        this(3);
    }

    public MyBlockingQueue(Integer size) {
        lock = new ReentrantLock();
        put = lock.newCondition();
        poll = lock.newCondition();

        this.size = size;
        queue = new ArrayList<>();
    }

    public void put(Integer item) {
        lock.lock();
        try {
            if (queue.size() < size) {
                log.info("put: {} ==> {}", item, Thread.currentThread().getName());
                queue.add(item);
                poll.signal();
            } else {
                log.info("start await put: ==> {}", Thread.currentThread().getName());
                put.await();
                log.info("end await put: ==> {}", Thread.currentThread().getName());
                put(item);
            }
        } catch (InterruptedException e) {
            log.warn("InterruptedException: {}", e.getMessage(), e);
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    public Integer poll() {
        lock.lock();
        try {
            if (queue.isEmpty()) {
                log.info("start await poll: ==> {}", Thread.currentThread().getName());
                poll.await();
                log.info("end await poll: ==> {}", Thread.currentThread().getName());
                return poll();
            } else {
                Integer item = queue.remove(0);
                put.signal();
                log.info("poll: {} ==> {}", item, Thread.currentThread().getName());
                return item;
            }
        } catch (InterruptedException e) {
            log.warn("InterruptedException: {}", e.getMessage(), e);
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }

        return null;
    }
}
