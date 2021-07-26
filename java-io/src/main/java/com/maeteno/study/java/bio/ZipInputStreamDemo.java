package com.maeteno.study.java.bio;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Slf4j
public class ZipInputStreamDemo {

    @SneakyThrows
    public static void main(String[] args) {
        InputStream inputStream = ZipInputStreamDemo.class.getResourceAsStream("demo.zip");
        assert inputStream != null;

        ZipInputStream zipInputStream = new ZipInputStream(inputStream);

        ZipEntry nextEntry = null;
        while ((nextEntry = zipInputStream.getNextEntry()) != null) {

        }
    }
}
