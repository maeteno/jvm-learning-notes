package com.maeteno.study.java.concurrent.aqs;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author Alan.Fu
 */
public class MyLock extends AbstractQueuedSynchronizer {

    @Override
    protected boolean tryAcquire(int arg) {
        compareAndSetState(0,1);
        setExclusiveOwnerThread(Thread.currentThread());
        return super.tryAcquire(arg);
    }

    @Override
    protected boolean tryRelease(int arg) {
        return super.tryRelease(arg);
    }

}
