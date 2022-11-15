package com.maeteno.study.java.base.iterable;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

class IterableExampleTest {

    @Test
    void testFor() {
        IterableExample example = new IterableExample();

        for (Integer item : example) {
            System.out.println(item);
        }
    }

    @Test
    void testWhile() {
        IterableExample example = new IterableExample();
        Iterator<Integer> iterator = example.iterator();

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    @Test
    void testStream() {
        IterableExample example = new IterableExample();
        Spliterator<Integer> spliterator = example.spliterator();
        StreamSupport.stream(spliterator, true)
                .forEach(it -> {
                    Thread currentThread = Thread.currentThread();
                    String msg = String.format("Thread ID: %s,%s \tNumber: %d", currentThread.getId(), currentThread.getName(), it);
                    System.out.println(msg);
                });
    }

    @Test
    void testStream2() {
        IterableExample example = new IterableExample();
        Spliterator<Integer> spliterator = example.spliterator();
        List<Integer> patch;
        do {
            // 分批次的获取数据
            // ⚠️ 这里需要注意的是，不能设置成并行获取，否则只能获取第一页
            patch = StreamSupport.stream(spliterator, false)
                    .limit(10)
                    .collect(Collectors.toList());

            // 对数据进行并行处理
            patch.parallelStream()
                    .forEach(it -> {
                        Thread currentThread = Thread.currentThread();
                        String msg = String.format("Thread ID: %s,%s \tNumber: %d", currentThread.getId(), currentThread.getName(), it);
                        System.out.println(msg);
                    });

            // 进行循环获取
        } while (!patch.isEmpty());
    }
}