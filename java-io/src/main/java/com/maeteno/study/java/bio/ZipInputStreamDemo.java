package com.maeteno.study.java.bio;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author Alan.Fu
 */
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
