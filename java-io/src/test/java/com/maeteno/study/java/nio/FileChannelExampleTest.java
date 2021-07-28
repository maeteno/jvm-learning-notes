package com.maeteno.study.java.nio;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileChannelExampleTest {
    private FileChannelExample fileChannelExample;

    @BeforeEach
    void setUp() {
        fileChannelExample = new FileChannelExample();
    }

    @Test
    void fileChannelByInputStream() {
        fileChannelExample.fileChannelByInputStream();
        Assertions.assertTrue(true);
    }

    @Test
    void fileChannelByOutputStream() {
        fileChannelExample.fileChannelByOutputStream();
        Assertions.assertTrue(true);
    }

    @Test
    void fileChannelByRandomAccessFile() {
        fileChannelExample.fileChannelByRandomAccessFile();
        Assertions.assertTrue(true);
    }
}