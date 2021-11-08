package com.maeteno.study.java.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class HashMapThreadExample {
    public static void main(String[] args) throws InterruptedException {
        final Map<String, String> map = new HashMap<>(2);
        Thread t = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                new Thread(() -> map.put(UUID.randomUUID().toString(), ""), "ftf" + i).start();
            }
        }, "ftf");
        t.start();
        t.join();

        Thread.sleep(10000L);
        log.info("Map Size: {}", map.size());
    }
}
