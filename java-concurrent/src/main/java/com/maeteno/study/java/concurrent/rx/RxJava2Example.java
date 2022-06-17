package com.maeteno.study.java.concurrent.rx;


import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

public class RxJava2Example {
    private final MySubscriber mySubscriber;

    public RxJava2Example(MySubscriber mySubscriber) {
        this.mySubscriber = mySubscriber;
    }

    public static void main(String[] args) {
        RxJava2Example java2Example = new RxJava2Example(new MySubscriber());
        java2Example.run();
    }

    public void run() {
        Flowable.create(this::subscribe, BackpressureStrategy.BUFFER)
                .map(i -> {
                    TimeUnit.MILLISECONDS.sleep(100);
                    return i;
                })
                .map(i -> {
                    TimeUnit.MILLISECONDS.sleep(100);
                    return i + 1;
                })
                .map(i -> {
                    TimeUnit.MILLISECONDS.sleep(100);
                    return i + 1;
                })
                .map(i -> {
                    TimeUnit.MILLISECONDS.sleep(100);
                    return i + 1;
                })
                .subscribe(mySubscriber);
    }

    public void subscribe(FlowableEmitter<Integer> emitter) {
        for (int i = 0; i < 10000; i++) {
            emitter.onNext(i);
        }

        emitter.onComplete();
    }

    @Slf4j
    public static class MySubscriber implements Subscriber<Integer> {
        private Subscription subscription;

        @Override
        public void onSubscribe(Subscription s) {
            subscription = s;
            subscription.request(6);
            log.info("Subscription: {}", s);
        }

        @Override
        public void onNext(Integer o) {
            log.info("On Next: {}", o);
            subscription.request(1);
        }

        @Override
        public void onError(Throwable t) {
            log.error("Throwable: {}", t.getMessage(), t);
        }

        @Override
        public void onComplete() {
            log.info("onComplete");
        }
    }
}
