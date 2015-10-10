package com.chriszou.rxjava;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;

/**
 * Created by ChrisZou on 9/30/15.
 */
public class CreateObservableDemo {
    public void start() {
        create();
        just();
        from();
    }

    private void just() {
        Observable<String> observable = Observable.just(singleValue());
        observable.subscribe(this::l);

        Observable<String> observable2 = Observable.just(oneValue(), anotherValue());
        observable2.subscribe(this::l);
    }

    private String anotherValue() {
        return "Two";
    }

    private <T> String oneValue() {
        return "One";
    }

    private String singleValue() {
        return "Hello";
    }

    private void create() {
        Observable<String> observable = Observable.create(new OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String result = getResult();
                subscriber.onNext(result);
                subscriber.onCompleted();
            }
        });
        observable.subscribe(this::l, this::l);
    }

    private void from() {
        Observable<String> observable = Observable.from(testingItems());
        observable.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                l("oncomplete");
            }

            @Override
            public void onError(Throwable e) {
                l("Error happened: "+e.getMessage());
            }

            @Override
            public void onNext(String s) {
                l("Got item: "+s);
            }
        });
    }

    private List<String> testingItems() {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < 5; i++) items.add("" + i);
        return items;
    }

    private String getResult() {
        String result = "Hey, you!"; //Imagine you get the result from somewhere else, say read from a file or a network.
        return result;
    }

    public void l(Object str) {
        if (str == null) str = "Message Null";
        Log.d("zyzy", str.toString());
    }
}
