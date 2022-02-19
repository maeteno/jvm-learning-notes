package com.maeteno.study.java.bio;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FilesExampleTest {
    private final FilesExample filesExample = new FilesExample();

    @Test
    void walkFileTree() {
        filesExample.walkFileTree();
        Assertions.assertTrue(true);
    }

    @Test
    void lines() {
        filesExample.lines();
        Assertions.assertTrue(true);
    }

    @Test
    void streamClose() {
        filesExample.streamClose();
        Assertions.assertTrue(true);
    }
}