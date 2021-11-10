package com.maeteno.study.java.concurrent.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class ExchangerTest {
    private static final Exchanger<String> exgr = new Exchanger<>();
    private static ExecutorService threadPool = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        threadPool.execute(() -> {
            try {
                String a = "银行流水AB";
                // A录入银行流水数据
                exgr.exchange(a);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        threadPool.execute(() -> {
            try {
                String b = "银行流水AB";
                // B录入银行流水数据
                String a = exgr.exchange(b);
                log.info("A和B数据是否一致：{} ，A录入的是：{}， B录入是：{}", a.equals(b), a, b);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        threadPool.shutdown();
    }
}
