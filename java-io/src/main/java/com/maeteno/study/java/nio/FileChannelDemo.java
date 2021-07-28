package com.maeteno.study.java.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

@Slf4j
public class FileChannelDemo {
    public static void main(String[] args) {
        URL resource = FileChannelDemo.class.getClassLoader().getResource("demo.txt");
        assert resource != null;

        try (FileChannel channel = new FileInputStream(resource.getFile()).getChannel()) {
            ByteBuffer buffer = ByteBuffer.allocate(5);

            while (channel.read(buffer) != -1) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    log.info("{}", buffer.get());
                }
                buffer.clear();
            }

        } catch (IOException exception) {
            log.error("IOException:", exception);
        }

        log.info("RandomAccessFile");
        try (FileChannel channel = new RandomAccessFile(resource.getFile(), "rw").getChannel()) {
            ByteBuffer buffer = ByteBuffer.allocate(20);
            channel.read(buffer);

            channel.write(StandardCharsets.UTF_8.encode("\n"));

            buffer.flip();
            channel.write(buffer);
        } catch (IOException exception) {
            log.error("IOException:", exception);
        }
    }
}
