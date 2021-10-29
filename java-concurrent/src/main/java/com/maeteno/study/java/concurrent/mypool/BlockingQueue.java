package com.maeteno.study.java.concurrent.mypool;

import java.util.ArrayDeque;
import java.util.Deque;

public class BlockingQueue<T> {
    private Deque<T> deque = new ArrayDeque<>();
    private Integer size;

    public BlockingQueue(Integer size) {
        deque = new ArrayDeque<>(size);
    }
}
