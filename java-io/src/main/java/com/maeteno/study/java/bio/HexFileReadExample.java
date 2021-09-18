package com.maeteno.study.java.bio;

import lombok.extern.slf4j.Slf4j;

/**
 * https://www.huaweicloud.com/articles/13542846.html
 *
 * @author Alan.Fu
 */
@Slf4j
public class HexFileReadExample {

    public static void main(String[] args) {
        // {0xFF, 0xD8, 0xFF, 0xE1}
        byte[] bytes = new byte[]{-1, -40, -1, -31, 1,127};


        for (byte aByte : bytes) {
            log.info("{}", aByte);
            log.info("{}", Integer.toHexString(aByte & 0xFF));
            log.info("{}", Integer.toHexString(aByte));
            log.info("==========================\n");
        }
    }
}
