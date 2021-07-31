package com.maeteno.study.java.concurrent.thread;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RunnableThreadExampleTest {
    private final RunnableThreadExample threadCreateExample = new RunnableThreadExample();

    @Test
    void createByThread() {
        threadCreateExample.createByThread();

        Assertions.assertTrue(true);
    }

    @Test
    void createByRunnable() {
        threadCreateExample.createByRunnable();

        Assertions.assertTrue(true);
    }

    @Test
    void createByLambda() {
        threadCreateExample.createByLambda();

        Assertions.assertTrue(true);
    }
}