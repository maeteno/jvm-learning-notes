package com.maeteno.study.collections;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author Alan.Fu
 */
@Slf4j
public class BlockingQueueExample {
    public static void main(String[] args) {

        // 阻塞队列继承与 BlockingQueue 接口。 juc 下的队列基本都是线程安全的
        // add 和 poll 是非阻塞的
        // put 和 take 是阻塞的
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    log.info("Put: {}", i);
                    queue.put(i);
                } catch (InterruptedException e) {
                    log.error("InterruptedException", e);
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                Integer item = null;
                try {
                    item = queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("Item 1: {}", item);
            }
        }, "read 2").start();

        new Thread(() -> {
            while (true) {
                Integer item = null;
                try {
                    item = queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("Item 1: {}", item);
            }
        }, "read 3").start();
    }
}
