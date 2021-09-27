package com.maeteno.study.java.nio;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author Alan.Fu
 */
@Slf4j
public class ByteBufferExample {
    private final File file;

    public ByteBufferExample() {
        file = FileUtil.touch("byte-buffer-example.txt");
        log.info("文件地址:{}", file.getPath());
    }

    public void read() {
        try (FileChannel channel = new RandomAccessFile(file, "rw").getChannel()) {
            // 将字符串转换为 ByteBuffer, 返回一个读模式的 ByteBuffer
            ByteBuffer content = StandardCharsets.UTF_8.encode("123ABC你好");
            channel.write(content);
        } catch (IOException exception) {
            log.error("IOException", exception);
        }

        try (FileChannel channel = new RandomAccessFile(file, "rw").getChannel()) {
            ByteBuffer buffer = ByteBuffer.allocate(5);
            while (channel.read(buffer) != -1) {
                buffer.flip();
                byte[] bytes = buffer.array();
                log.info("{}", bytes);
                buffer.clear();
            }
        } catch (IOException exception) {
            log.error("IOException", exception);
        }
    }
}
