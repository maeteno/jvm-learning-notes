package com.maeteno.study.java.base.generic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GenericExample2Test {
    private GenericExample2<String> genericExample2;

    @BeforeEach
    void setUp() {
        genericExample2 = new GenericExample2<>();
    }

    @Test
    void demo() {
        genericExample2.demo("1234");
        // genericExample2.demo(1234);
    }
}