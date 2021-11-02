package com.maeteno.study.java.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

@Slf4j
public class MultiThread {
    public static void main(String[] args) {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

        ThreadInfo[] info = threadMXBean.dumpAllThreads(false, false);

        for (ThreadInfo threadInfo : info) {
            log.info("[{}]: {}", threadInfo.getThreadId(), threadInfo.getThreadName());
        }
    }
}
