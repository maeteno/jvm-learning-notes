package com.maeteno.study.java.concurrent.forkjoin;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuickSortExampleTest {
    @Test
    void quickSort() throws ExecutionException, InterruptedException {
        // given
        var list = new ArrayList<Integer>(10);
        list.add(6);
        list.add(5);
        list.add(3);
        list.add(4);
        list.add(8);
        list.add(9);
        list.add(0);
        list.add(2);
        list.add(7);
        list.add(1);

        // when
        var quick = new QuickSortExample(list);
        ForkJoinPool pool = ForkJoinPool.commonPool();
        ForkJoinTask<List<Integer>> task = pool.submit(quick);

        // then
        List<Integer> result = task.get();
        assertEquals(0, result.get(0));
    }
}