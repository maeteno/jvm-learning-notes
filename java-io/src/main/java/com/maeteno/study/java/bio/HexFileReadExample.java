package com.maeteno.study.java.bio;

import cn.hutool.core.io.resource.ResourceUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alan.Fu
 */
@Slf4j
public class HexFileReadExample {

    public static void main(String[] args) {
        Map<String, int[]> extMap = new HashMap<>();
        extMap.put("jpg", new int[]{0xFF, 0xD8, 0xFF, 0xE1});
        extMap.put("png", new int[]{0xFF, 0xD8, 0xFF, 0xE6});

        byte[] bytes = new byte[4];
        try (InputStream inputStream = ResourceUtil.getStream("demo.jpg")) {
            int count = inputStream.read(bytes);
            if (count != 4) {
                log.error("文件格式读取失败: {}", count);
            }
        } catch (IOException exception) {
            log.error("IOException: {}", exception.getMessage(), exception);
        }

        String ext = extMap.entrySet().stream()
                .filter(entry -> {
                    int[] value = entry.getValue();
                    for (int i = 0; i < bytes.length; i++) {
                        if ((bytes[i] & 0xFF) != value[i]) {
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
