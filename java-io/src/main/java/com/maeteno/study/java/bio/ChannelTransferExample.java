package com.maeteno.study.java.bio;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.FileChannel;

/**
 * FileChannel Demo
 *
 * @author Alan.Fu
 */
@Slf4j
public class ChannelTransferExample {

    @SneakyThrows
    public void channelTransfer() {
        URL url = ResourceUtil.getResource("demo.txt");
        File file = FileUtil.touch("demo-copy.txt");
        File file2 = FileUtil.touch("demo-copy-1.txt");

        try (FileChannel inputChannel = new FileInputStream(url.getPath()).getChannel();
             FileChannel outputChannel = new FileOutputStream(file).getChannel()) {
            inputChannel.transferTo(0, inputChannel.size(), outputChannel);
        }

        try (FileChannel inputChannel = new FileInputStream(url.getPath()).getChannel();
             FileChannel outputChannel = new FileOutputStream(file2).getChannel()) {
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        }
    }
}
