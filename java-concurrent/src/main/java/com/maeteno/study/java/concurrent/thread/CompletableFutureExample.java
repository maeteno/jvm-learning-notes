package com.maeteno.study.java.concurrent.thread;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

/**
 * @author Alan.Fu
 */
@Slf4j
public class CompletableFutureExample {

    @SneakyThrows
    public static void main(String[] args) {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> 100);
        completableFuture.thenAccept(r -> System.out.println("Result: " + r));


        Thread.sleep(1000L);
    }
}
