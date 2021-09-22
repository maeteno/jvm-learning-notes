package com.maeteno.study.java.base.generic;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Alan.Fu
 */
@Slf4j
public class GenericDemo<T extends ClassA> {

    public void print(T arg) {
        log.info("T => {}", arg.toString());
    }
}
