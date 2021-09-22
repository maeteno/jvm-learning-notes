package com.maeteno.study.java.base;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegularExampleTest {
    private static RegularExample regularExample;

    @BeforeAll
    public static void setUp() {
        regularExample = new RegularExample();
    }

    @Test
    void baseDemo() {
        regularExample.baseDemo();
    }

    @Test
    void groupDemo() {
        regularExample.groupDemo();
    }

    @Test
    void strDemo() {
        regularExample.strDemo();
    }
}