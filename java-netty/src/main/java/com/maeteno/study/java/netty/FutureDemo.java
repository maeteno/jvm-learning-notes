package com.maeteno.study.java.netty;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Alan.Fu
 */
@Slf4j
public class FutureDemo {
    public static void main(String[] args) {
        NioEventLoopGroup executors = new NioEventLoopGroup();

        Future<Long> future = executors.submit(() -> {
            Thread.sleep(1000L);
            return System.currentTimeMillis();
        });

        future.addListener(future1 -> log.info("Result: {}", future1.get()));

        executors.shutdownGracefully();
    }
}
