package com.maeteno.study.java.nio;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
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
    private final Selector boss;
    private final Selector work;

    private ServerSocketChannelExample() throws IOException {
        boss = Selector.open();
        work = Selector.open();
    }

    public static ServerSocketChannelExample open() throws IOException {
        return new ServerSocketChannelExample();
    }

    @SneakyThrows
    public void run() {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(6379));
        serverSocketChannel.register(boss, SelectionKey.OP_ACCEPT);

        Thread bossThread = new Thread(this::runBoss);
        bossThread.start();

        Thread workThread = new Thread(this::runWork);
        workThread.setDaemon(true);
        workThread.start();
    }

    @SneakyThrows
    private void runBoss() {
        log.info("Boss 线程启动...");
        while (boss.select() > 0) {
            log.info("Boss 获得监听...");
            Set<SelectionKey> selectionKeys = boss.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();

            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();

                if (key.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    // 接收一个连接
                    SocketChannel socketChannel = channel.accept();
                    // 设置为非阻塞
                    socketChannel.configureBlocking(false);
                    // 注册这个, work 已经在其他线程监听，该注册无法生效
                    socketChannel.register(work, SelectionKey.OP_READ);
                    log.info("Server Acceptable");
                }

                keyIterator.remove();
            }
        }
    }

    @SneakyThrows
    private void runWork() {
        if (work.keys().isEmpty()) {
            log.info("无监听...");
            Thread.sleep(1000L);
        } else {
            while (work.select(1000L) > 0) {
                Set<SelectionKey> selectionKeys = work.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectionKeys.iterator();

                ByteBuffer buffer = ByteBuffer.allocate(100);

                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();

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

        runWork();
    }
}
