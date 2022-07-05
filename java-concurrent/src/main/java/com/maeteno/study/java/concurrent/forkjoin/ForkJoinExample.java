package com.maeteno.study.java.concurrent.forkjoin;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author Alan.Fu
 */
@Slf4j
public class ForkJoinExample extends RecursiveTask<Integer> {
    private static final int THRESHOLD = 10;
    private final int start;
    private final int end;

    public ForkJoinExample(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool pool = new ForkJoinPool();

        ForkJoinExample example = new ForkJoinExample(1, 100);
        ForkJoinTask<Integer> task = pool.submit(example);

        log.info("Sum: {}", task.get());
    }

    @Override
    protected Integer compute() {
        boolean canCompute = (end - start) <= THRESHOLD;

        int sum = 0;
        if (canCompute) {
            for (int i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            int middle = (start + end) / 2;

            ForkJoinExample left = new ForkJoinExample(start, middle);
            ForkJoinExample right = new ForkJoinExample(middle + 1, end);

            left.fork();
            right.fork();

            sum = left.join() + right.join();
        }

        return sum;
    }
}