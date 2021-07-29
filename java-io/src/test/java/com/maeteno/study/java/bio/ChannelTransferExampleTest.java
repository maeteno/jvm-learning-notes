package com.maeteno.study.java.bio;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ChannelTransferExampleTest {
    private final ChannelTransferExample example = new ChannelTransferExample();

    @Test
    void channelTransfer() {
        example.channelTransfer();
        Assertions.assertTrue(true);
    }
}