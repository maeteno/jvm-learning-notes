package com.maeteno.study.java.concurrent.queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MyBlockingQueueTest {
    MyBlockingQueue queue;

    @BeforeEach
    void setUp() {
        queue = new MyBlockingQueue();
    }

    @Test
    void testPut() throws InterruptedException {
        Thread thread = new Thread(() -> {
            queue.put(1);
            queue.put(2);
            queue.put(3);
            queue.put(4);
        });

        Thread thread1 = new Thread(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            queue.poll();
        });
        thread.start();
        thread1.start();

        thread.join();
        thread1.join();

        assertTrue(true);
    }

    @Test
    void testPoll() throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            queue.poll();
            queue.poll();
        }, "poll-1");

        Thread thread3 = new Thread(() -> {
            queue.poll();
            queue.poll();
            queue.poll();
        }, "poll-2");

        Thread thread2 = new Thread(() -> {
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            queue.put(1);
            queue.put(2);
            queue.put(3);
        }, "put-1");

        Thread thread4 = new Thread(() -> {
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            queue.put(4);
            queue.put(5);
        }, "put-2");

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        thread2.join();
        thread1.join();
        thread3.join();
        thread4.join();

        assertTrue(true);
    }
}