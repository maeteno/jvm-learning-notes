package com.maeteno.study.java.base.generic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GenericExample3Test {
    private GenericExample3<Integer> genericExample3;

    @BeforeEach
    void setUp() {
        genericExample3 = new GenericExample3<>();
    }

    @Test
    void demo() {
        genericExample3.demo(123);
    }
}