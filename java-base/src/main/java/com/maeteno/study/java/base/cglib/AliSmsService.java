package com.maeteno.study.java.base.cglib;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AliSmsService {
    public String send(String message) {
       log.info("send message: {}", message);
        return message;
    }
}
