package com.maeteno.study.java.base.generic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GenericExampleTest {
    private GenericExample3<? super Integer> genericExample3;

    @BeforeEach
    void setUp() {
        genericExample3 = new GenericExample3<>();
    }

    @Test
    void demo() {
        genericExample3.demo(123);
    }
}