package com.maeteno.study.java.concurrent.pool;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

/**
 * @author Alan.Fu
 */
@Slf4j
public class MyThreadPoolExecutor implements Executor {
    final BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(10);

    public MyThreadPoolExecutor(int poolSize) {
        for (int i = 1; i <= poolSize; i++) {
            new Thread(() -> {
                while (true) {
                    try {
                        // 阻塞获取队列的任务执行
                        Runnable runnable = workQueue.take();
                        runnable.run();
                    } catch (InterruptedException e) {
                        log.error("InterruptedException", e);
                    }
                }
            }, "MyThreadPoolExecutor-" + i).start();
        }
    }

    @Override
    public void execute(Runnable command) {
        Objects.requireNonNull(command);

        try {
            // 提交任务到队列
            workQueue.put(command);
        } catch (InterruptedException e) {
            log.error("InterruptedException", e);
            throw new RejectedExecutionException("任务繁忙");
        }
    }
}
