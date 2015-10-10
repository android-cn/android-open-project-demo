package com.chriszou.rxjava;

import android.util.Log;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by ChrisZou on 10/10/15.
 */
public class UnsubscribeDemo {
    public void start() {
        Subscription subscription = Observable.create(new OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String result = getResult();
                Subscription subscription = new Subscription() {
                    boolean unsubscribed = false;

                    @Override
                    public void unsubscribe() {
                        unsubscribed = true;
                        cancelGettingResult();
                    }

                    @Override
                    public boolean isUnsubscribed() {
                        return unsubscribed;
                    }
                };

                subscriber.add(subscription);
                subscriber.onNext(result);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).subscribe(this::l, this::l);
    }

    private void cancelGettingResult() {
        l("cancel getting result: " + Thread.currentThread().getId());
    }

    private String getResult() {
        l("Start getting result: " + Thread.currentThread().getId());
        try {
            Thread.sleep(10000);
            return "This is the result";
        } catch (InterruptedException e) {
            e.printStackTrace();
            l("Exception: " + e.getMessage());
            return e.getMessage();
        }
    }

    public void l(Object str) {
        if (str == null) str = "Message Null";
        Log.d("zyzy", str.toString());
    }

}
