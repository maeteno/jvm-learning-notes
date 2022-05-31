package com.maeteno.study.java.concurrent.stream;

import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.StreamSupport;

@Slf4j
public class StreamDemo01 {
    public static void main(String[] args) {
        Iterable<Integer> iterable = new MyIterable();
        StreamSupport.stream(iterable.spliterator(), true)
                .parallel()
                .map(it -> {
                    log.info("map: {} <== {}", it, Thread.currentThread().getName());
                    if (it % 5 == 0) {
                        try {
                            Thread.sleep(1000L);
                        } catch (Exception e) {
                            log.warn("Exception: {}", e.getMessage(), e);
                        }
                    }
                    return it.toString();
                })
                .forEach(it -> log.info("for each: {} <== {}", it, Thread.currentThread().getName()));

        int poolSize2 = ForkJoinPool.commonPool().getPoolSize();
        log.info("Pool Size: {}", poolSize2);
    }

    static class MyIterable implements Iterable<Integer> {
        /**
         * Returns an iterator over elements of type {@code T}.
         *
         * @return an Iterator.
         */
        @Override
        public Iterator<Integer> iterator() {
            return new MyIterator();
        }
    }

    static class MyIterator implements Iterator<Integer> {
        private final Integer total = 100;
        private Integer index = 0;

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return ++index < total;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public Integer next() {
            log.info("next: {} <== {}", index, Thread.currentThread().getName());
            if (index < total) {
                return index;
            }
            throw new NoSuchElementException();
        }
    }
}
