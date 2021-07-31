package com.maeteno.study.java.concurrent.thread;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FutureTaskThreadExampleTest {
    private final FutureTaskThreadExample futureTaskThreadExample = new FutureTaskThreadExample();

    @Test
    void callableDemo() {
        futureTaskThreadExample.callableDemo();
        Assertions.assertTrue(true);
    }
}