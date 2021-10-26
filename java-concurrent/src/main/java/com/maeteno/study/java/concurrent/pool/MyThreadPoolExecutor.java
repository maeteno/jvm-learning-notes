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
                        Runnable runnable = workQueue.take();
                        runnable.run();
                    } catch (InterruptedException e) {
                        log.error("InterruptedException", e);
                    }
                }
            }, "MyThreadPoolExecutor-" + i).start();
        }
    }

    /**
     * Executes the given command at some time in the future.  The command
     * may execute in a new thread, in a pooled thread, or in the calling
     * thread, at the discretion of the {@code Executor} implementation.
     *
     * @param command the runnable task
     * @throws RejectedExecutionException if this task cannot be
     *                                    accepted for execution
     * @throws NullPointerException       if command is null
     */
    @Override
    public void execute(Runnable command) {
        Objects.requireNonNull(command);

        try {
            workQueue.put(command);
        } catch (InterruptedException e) {
            log.error("InterruptedException", e);
            throw new RejectedExecutionException("任务繁忙");
        }
    }
}
