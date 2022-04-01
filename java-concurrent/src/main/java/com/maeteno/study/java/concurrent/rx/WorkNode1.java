package com.maeteno.study.java.concurrent.rx;

import lombok.extern.slf4j.Slf4j;
import org.redisson.RedissonNode;
import org.redisson.config.RedissonNodeConfig;

import java.util.Collections;

import static com.maeteno.study.java.concurrent.rx.WorkStart.REDIS_CONFIG;

/**
 * @author 5YKF5Y+M5Lqu
 */
@Slf4j
public class WorkNode1 {

    public static void main(String[] args) {
        var nodeConfig = new RedissonNodeConfig(REDIS_CONFIG);
        nodeConfig.setExecutorServiceWorkers(Collections.singletonMap("executor-service-3", 10));
        var node = RedissonNode.create(nodeConfig);
        node.start();
    }
}
