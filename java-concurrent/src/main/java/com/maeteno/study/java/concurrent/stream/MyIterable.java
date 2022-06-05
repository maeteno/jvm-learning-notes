package com.maeteno.study.java.concurrent.stream;

import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.NoSuchElementException;

class MyIterable implements Iterable<Integer> {
    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Integer> iterator() {
        return new MyIterator();
    }

    @Slf4j
    static class MyIterator implements Iterator<Integer> {
        private final Integer total = 100;
        private Integer index = 0;

        @Override
        public boolean hasNext() {
            return ++index < total;
        }

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
