package com.maeteno.leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BaseCalculateTest {
    @Test
    void calculate() {
        BaseCalculate calc = new BaseCalculate();
        int result = calc.calculate("1+2");
        Assertions.assertEquals(3, result);
    }
}