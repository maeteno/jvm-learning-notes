package com.maeteno.study.java.nio;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Alan.Fu
 */
@Slf4j
public class SocketChannelExample {

    public static void main(String[] args) {
        try {
            client();
        } catch (Exception e) {
            log.error("Exception", e);
        }
    }

    @SneakyThrows
    public static void client() {
        SocketAddress remote = new InetSocketAddress("localhost", 6379);

        try (Selector selector = Selector.open()) {
            final SocketChannel socketChannel = SocketChannel.open(remote);
            socketChannel.configureBlocking(false);

            socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

            while (selector.select() > 0) {
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    SocketChannel channel = (SocketChannel) key.channel();

                    // 可写
                    if (key.isWritable()) {
                        log.info("is writable");
                        channel.write(ByteBuffer.wrap("123".getBytes(StandardCharsets.UTF_8)));
                    }

                    // 可读
                    if (key.isReadable()) {
                        log.info("is readable");
                        ByteBuffer buffer = ByteBuffer.allocate(20);

                        while (channel.read(buffer) > 0) {
                            buffer.flip();

                            byte[] bytes = buffer.array();
                            log.info("bytes: {}", bytes);

                            buffer.clear();
                        }

//                        try {
//                            channel.close();
//                        } catch (IOException e) {
//                            log.error("IOException", e);
//                        }

                        channel.finishConnect();
                    }

                    keyIterator.remove();
                }

                if (selector.keys().isEmpty()) {
                    break;
                }
            }
        }
    }
}
