package com.maeteno.study.java.base.annotation;

public class Demo {
    @Version
    public String version() {
        System.out.println("Demo Version");
        return "1.0.0";
    }
}
