package com.maeteno.study.java.base.generic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GenericExample1Test {
    private GenericExample1 genericExample1;

    @BeforeEach
    void setUp() {
        genericExample1 = new GenericExample1();
    }

    @Test
    void demo() {
        genericExample1.demo("1234");
        genericExample1.demo(1123);
    }
}