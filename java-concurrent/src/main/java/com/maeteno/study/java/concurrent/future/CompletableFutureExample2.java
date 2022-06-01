package com.maeteno.study.java.concurrent.future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author Warlock-Team
 */
@Slf4j
public class CompletableFutureExample2 {
    public static void main(String[] args) {
        CompletableFuture<Void> cf1 = CompletableFuture.runAsync(() -> {
            sleep(1, TimeUnit.MILLISECONDS);
            log.info("main: {}", Thread.currentThread().getName());
            sleep(15, TimeUnit.SECONDS);
        });

        cf1.thenAccept(r -> {
            log.info("thenAccept: {} {}", r, Thread.currentThread().getName());
        });
    }

    static void sleep(int t, TimeUnit u) {
        try {
            u.sleep(t);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
