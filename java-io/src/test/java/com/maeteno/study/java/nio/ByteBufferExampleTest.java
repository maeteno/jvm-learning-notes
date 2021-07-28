package com.maeteno.study.java.nio;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ByteBufferExampleTest {
    private ByteBufferExample byteBufferExample;

    @BeforeEach
    void setUp() {
        byteBufferExample = new ByteBufferExample();
    }

    @Test
    void read() {
        byteBufferExample.read();

        Assertions.assertTrue(true);
    }
}