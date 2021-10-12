package com.maeteno.study.java.netty;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Alan.Fu
 */
@Slf4j
public class PromiseDemo {
    public static void main(String[] args) {
        // 1. 获取一个EventLoop对象
        NioEventLoopGroup executors = new NioEventLoopGroup();
        EventLoop loop = executors.next();
        // 2. 通过 EventLoop 创建一个Promise
        DefaultPromise<Integer> promise = new DefaultPromise<>(loop);
        // 3.在其他线程中使用promise对象
        executors.execute(() -> {
            log.info("execute");
            promise.setSuccess(1000);
        });

        // 4. 阻塞获取结果
        // <code>log.info("Promise: {}", promise.get());</code>
        // 5. 异步获取结果
        promise.addListener(p -> {
            log.info("ex");
            log.info("Promise: {}", promise.getNow());
        });

        executors.shutdownGracefully();
    }
}
