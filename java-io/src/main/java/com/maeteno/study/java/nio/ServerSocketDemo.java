package com.maeteno.study.java.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author Alan.Fu
 */
@Slf4j
public class ServerSocketDemo {

    public static void main(String[] args) {
        try {
            ServerSocketChannelExample server = ServerSocketChannelExample.open();
            server.run();
        } catch (IOException e) {
            log.error("服务启动失败", e);
        }
    }
}
