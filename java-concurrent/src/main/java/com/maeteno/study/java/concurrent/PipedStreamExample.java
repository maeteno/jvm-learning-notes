package com.maeteno.study.java.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * @author Alan.Fu
 */
@Slf4j
public class PipedStreamExample {
    public static void main(String[] args) throws IOException {
        PipedReader in = new PipedReader();
        PipedWriter out = new PipedWriter();

        try {
            out.connect(in);
        } catch (IOException e) {
            log.error("IOException", e);
        }

        new Thread(new Print(in)).start();

        int receive = 0;
        try {
            while ((receive = System.in.read()) != '#') {
                log.info("{}\t", (char) receive);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    @Slf4j
    static class Print implements Runnable {
        private final PipedReader reader;

        public Print(PipedReader reader) {
            this.reader = reader;
        }

        @Override
        public void run() {
            int receive = 0;
            try {
                while ((receive = reader.read()) != -1) {
                    log.info("{}\t", (char) receive);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
