package com.maeteno.study.java.nio;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

@Slf4j
public class FileChannelExample {
    private final URL url;

    public FileChannelExample() {
        url = FileChannelExample.class.getClassLoader().getResource("demo.txt");
    }

    /// 获取的Channel的权限和他的上级流一致

    /**
     * 通过InputStream获取Channel，该Channel未只读
     */
    public void fileChannelByInputStream() {
        try (FileChannel channel = new FileInputStream(url.getFile()).getChannel()) {
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
    }

    /**
     * 通过InputStream获取Channel，该Channel未只读
     */
    public void fileChannelByOutputStream() {
        String filePath = url.getPath() + "/../demo-1.txt";
        File file = FileUtil.file(filePath);
        try (FileChannel channel = new FileOutputStream(file).getChannel()) {
            ByteBuffer buffer1 = StandardCharsets.UTF_8.encode("FileChannelByOutputStream\n");
            ByteBuffer buffer2 = StandardCharsets.UTF_8.encode("FileChannelByOutputStream\n");
            channel.write(new ByteBuffer[]{buffer1, buffer2});
        } catch (IOException exception) {
            log.error("IOException:", exception);
        }
    }

    /**
     * 通过RandomAccessFile获取Channel，该Channel的读写和创建的RandomAccessFile一致
     */
    public void fileChannelByRandomAccessFile() {
        log.info("RandomAccessFile");
        try (FileChannel channel = new RandomAccessFile(url.getFile(), "rw").getChannel()) {
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
