package com.maeteno.study.java.bio;

import cn.hutool.core.io.resource.ResourceUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * https://www.huaweicloud.com/articles/13542846.html
 *
 * @author Alan.Fu
 */
@Slf4j
public class ByteReadExample {

    public static void main(String[] args) {
        Map<String, byte[]> extMap = new HashMap<>(2);
        extMap.put("jpg", new byte[]{127, 0xD, 0x8, 0xF, 0xF, 0xE, 0x1});
        extMap.put("png", new byte[]{0x11, 0xF, 0xD, 0x8, 0xF, 0xF, 0xE, 0x6});

        byte[] bytes = new byte[8];
        try (InputStream inputStream = ResourceUtil.getStream("demo.jpg")) {
            int count = inputStream.read(bytes);
            if (count != 8) {
                log.error("文件格式读取失败: {}", count);
            }
        } catch (IOException exception) {
            log.error("IOException: {}", exception.getMessage(), exception);
        }

        String ext = extMap.entrySet().stream()
                .filter(entry -> {
                    byte[] value = entry.getValue();

                    for (int i = 0; i < bytes.length; i++) {
                        byte aByte = bytes[i];
                        byte bByte = value[i];
                        int aInt = aByte & 0xFF;

                        if (aInt != bByte) {
                            return false;
                        }
                    }
                    return true;
                })
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse("png");

        log.info("file ext: {}", ext);
    }
}
