package com.maeteno.study.java.base.generic;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Alan.Fu
 */
@Slf4j
public class GenericExample1 {
    public <T> void demo(T arg) {
        log.info("{}", arg.toString());
    }
}
