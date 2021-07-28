package com.maeteno.study.java.base;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DateExampleTest {
    private final DateExample dateExample = new DateExample();

    @Test
    void strToDate() {
        dateExample.strToDate();
        Assertions.assertTrue(true);
    }

    @Test
    void dateToStr() {
        dateExample.dateToStr();
        Assertions.assertTrue(true);
    }
}