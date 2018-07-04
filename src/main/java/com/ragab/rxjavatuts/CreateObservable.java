/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ragab.rxjavatuts;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import org.reactivestreams.Subscriber;

/**
 *
 * @author ragab
 */
public class CreateObservable {

    public static void main(String[] args) {
        Observable<String> oneStringObjectObservable = Observable.just("item1"); // create observable from one String object 
        Observable<String> arrayOfStringObservable = Observable.just("first item", "second item");
        Observable<Integer> arrayOfIntegerObservable = Observable.just(1, 2, 3);

        Observable<Integer> rangeObservable = Observable.range(0, 10);  // it is equal to the next line 
        Observable<Integer> arrayOfIntegerObservable2 = Observable.fromArray(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        
        // it is useful instead of emitting null in it only onComplete method will be called 
        Observable.empty(); 
        
        // in it only onError method will be called 
        Observable.error(new Exception("no data found")); 
        
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> oe) throws Exception {
                oe.onNext(1);
                oe.onNext(2);
                oe.onNext(3);
                oe.onNext(4);
                oe.onNext(5);
                oe.onComplete();
            }
        });

        Observable<Integer> integerStream = Observable.create(subscriber -> {
            subscriber.onNext(1);
            subscriber.onNext(2);
            subscriber.onNext(3);
            subscriber.onNext(4);
            subscriber.onNext(5);
            subscriber.onComplete();
        });

        integerStream
                .subscribe(
                        i -> System.out.println("Received: " + i),
                        err -> System.out.println("BOOM"),
                        () -> System.out.println("Completion")
                );
    }

}
