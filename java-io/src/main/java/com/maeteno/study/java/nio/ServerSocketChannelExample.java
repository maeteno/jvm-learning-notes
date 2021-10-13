package com.maeteno.study.java.nio;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Alan.Fu
 */
@Slf4j
public class ServerSocketChannelExample {

    public static void main(String[] args) {
        try {
            server();
        } catch (Exception e) {
            log.error("Server Exception", e);
        }
    }

    @SneakyThrows
    public static void server() {
        Selector selector = Selector.open();

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);

        serverSocketChannel.socket().bind(new InetSocketAddress(6379));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (selector.select() > 0) {
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();

            ByteBuffer buffer = ByteBuffer.allocate(10);

            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();

                if (key.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    // 接收一个连接
                    SocketChannel socketChannel = channel.accept();
                    // 设置为非阻塞
                    socketChannel.configureBlocking(false);
                    // 注册这个
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    log.info("Server Acceptable");
                }

                if (key.isReadable()) {
                    log.info("Server Readable");
                    SocketChannel channel = (SocketChannel) key.channel();

                    while (channel.read(buffer) > 0) {
                        buffer.flip();
                        log.info("{}", buffer.array());
                        buffer.clear();
                    }

                    try {
                        channel.write(ByteBuffer.wrap("End...".getBytes(StandardCharsets.UTF_8)));
                    } catch (Exception e) {
                        log.error("Exception ", e);
                    }

                    // channel.close();
                }

                keyIterator.remove();
            }

        }
    }
}
