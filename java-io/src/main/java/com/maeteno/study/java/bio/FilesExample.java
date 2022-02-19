package com.maeteno.study.java.bio;

import cn.hutool.core.io.resource.ResourceUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author 5YKF5Y+M5Lqu
 */
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

    @SneakyThrows
    public void streamClose() {
        URL url = ResourceUtil.getResource("demo.txt");

        String filePath = url.getFile();
        File file = new File(filePath);

        Reader reader = new FileReader(file);

        BufferedReader bufferedReader = new BufferedReader(reader);
        bufferedReader.mark((int)file.length() + 1);
        bufferedReader.reset();

        LineNumberReader lineNumberReader1 = new LineNumberReader(bufferedReader);

        String s = lineNumberReader1.readLine();
        log.info("lineNumberReader1 => start");
        while (Objects.nonNull(s)) {
            log.info("lineNumberReader1: {}", s);
            s = lineNumberReader1.readLine();
        }
        log.info("=======================");

        //bufferedReader.mark((int)file.length() + 1);
        bufferedReader.reset();
        LineNumberReader lineNumberReader2 = new LineNumberReader(bufferedReader);
        String s2 = lineNumberReader2.readLine();
        log.info("lineNumberReader2 => start");
        while (Objects.nonNull(s2)) {
            log.info("lineNumberReader2: {}", s2);
            s2 = lineNumberReader2.readLine();
        }
    }
}
