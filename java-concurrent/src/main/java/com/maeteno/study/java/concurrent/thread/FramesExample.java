package com.maeteno.study.java.concurrent.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * Debug 中的 Frames 就表示的是线程的栈帧。
 */
@Slf4j
public class FramesExample {

    public static void main(String[] args) {
        fun1();
        int a = fun2();
        log.info("a:{}", a);
    }

    private static void fun1() {
        log.info("fun1");
    }

    private static Integer fun2() {
        int x = 1;
        int y = 2;
        fun1();
        fun3();
        return x + y;
    }

    private static void fun3() {
        log.info("fun3");
    }
}
