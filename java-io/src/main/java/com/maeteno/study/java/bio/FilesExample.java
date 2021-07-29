package com.maeteno.study.java.bio;

import cn.hutool.core.io.resource.ResourceUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.stream.Stream;

@Slf4j
public class FilesExample {

    @SneakyThrows
    public void walkFileTree() {
        Path path = Paths.get("./").normalize().toAbsolutePath();

        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                log.info("File Path: {}", file);
                return super.visitFile(file, attrs);
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                log.info("Dir Path: {}", dir);
                return super.postVisitDirectory(dir, exc);
            }
        });
    }

    @SneakyThrows
    public void lines() {
        URL url = ResourceUtil.getResource("demo.txt");
        Path path = Paths.get(url.getPath()).normalize();

        try (Stream<String> lines = Files.lines(path)) {
            lines.forEach(line -> log.info("Line: {}", line));
        }
    }
}
