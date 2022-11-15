package com.maeteno.study.java.concurrent.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class PrintABCByAtomicTag {

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        AtomicInteger tag = new AtomicInteger(0);
        threadPool.submit(() -> {
            int i = 0;
            while (i < 10) {
                while (tag.get() == 0) {
                    System.out.print("A");
                    tag.set(1);
                    i++;
                }
            }
        });

        threadPool.submit(() -> {
            int i = 0;
            while (i < 10) {
                while (tag.get() == 1) {
                    System.out.print("B");
                    i++;
                    tag.set(2);
                }
            }
        });

        threadPool.submit(() -> {
            int i = 0;
            while (i < 10) {
                while (tag.get() == 2) {
                    System.out.print("C");
                    i++;
                    tag.set(0);
                }
            }
        });

        threadPool.shutdown();
    }
}
