package com.maeteno.study.java.base.reflect;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Alan.Fu
 */
@Slf4j
public class DemoClass {
    public String pubFiled;
    private String prvFiled;

    public DemoClass() {

    }

    public DemoClass(String arg1) {
        this(arg1, arg1);
    }

    private DemoClass(String arg1, String arg2) {
        prvFiled = arg1;
        pubFiled = arg2;
    }

    public void method1() {
        log.info("method1 prvFiled: {}", prvFiled);
        method3();
    }

    public int method2(String arg1) {
        log.info("method2 arg1: {}", arg1);
        return method4(arg1);
    }

    private void method3() {
        log.info("method3 pubFiled: {}", pubFiled);
    }

    private int method4(String arg1) {
        log.info("method4 arg1: {}", arg1);
        return arg1.length();
    }
}
