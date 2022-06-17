package com.maeteno.study.java.concurrent.redisson;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.ExecutorOptions;
import org.redisson.config.Config;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * @author 5YKF5Y+M5Lqu
 */
@Slf4j
public class WorkStart {
    public static final Config REDIS_CONFIG = new Config();
    public static final Integer TEST_COUNT = 10000;

    static {
        REDIS_CONFIG.useSentinelServers()
                .setMasterName("my-master")
                .addSentinelAddress("redis://192.168.1.21:26380")
                .addSentinelAddress("redis://192.168.1.21:26381")
                .addSentinelAddress("redis://192.168.1.21:26382");
    }

    public static void main(String[] args) {
        var redisson = Redisson.create(REDIS_CONFIG);
        var options = ExecutorOptions.defaults();

        options.taskRetryInterval(2, TimeUnit.MINUTES);

        var executorService = redisson.getExecutorService("executor-service-3", options);

        if (executorService.isShutdown()) {
            log.error("ExecutorService is in shutdown state");
            return;
        }

        for (int i = 1; i < TEST_COUNT; i++) {
            try {
                Thread.sleep(10L);
            } catch (InterruptedException e) {
                log.error("{}", e.getMessage(), e);
                Thread.currentThread().interrupt();
            }

            executorService.submit(new Work(i));
        }
    }

    @Slf4j
    public static class Work implements Runnable, Serializable {
        private static final long serialVersionUID = -596177810013647485L;
        private final Integer value;

        public Work(Integer value) {
            this.value = value;
        }

        @Override
        public void run() {
            log.warn("Value: {}", value);
        }
    }
}
