package com.maeteno.study.java.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.stream.IntStream;

/**
 * @author Alan.Fu
 */
@Slf4j
public class NettyClient2 {
    public static void main(String[] args) {
        NioEventLoopGroup loopGroup = new NioEventLoopGroup(10);

        Bootstrap bootstrap = new Bootstrap()
                .channel(NioSocketChannel.class)
                .group(loopGroup)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                        ch.pipeline().addLast(new StringEncoder());
                    }
                });

        IntStream.range(0, 500)
                .mapToObj(i -> bootstrap.connect(new InetSocketAddress(8081)))
                .forEach(channelFuture -> channelFuture.addListener((ChannelFutureListener) future -> {
                    future.channel().writeAndFlush("1234567890");
                    future.channel().close();

                    ChannelFuture closeFuture = future.channel().closeFuture();
                    closeFuture.addListener((ChannelFutureListener) future1 -> {
                        // 因为 eventLoop 本身是一个执行器，所以不关闭该线程池程序不会退出。
                        log.debug("close");
                    });
                }));

        // loopGroup.shutdownGracefully();
    }
}
