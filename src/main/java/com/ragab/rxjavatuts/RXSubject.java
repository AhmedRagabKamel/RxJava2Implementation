/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ragab.rxjavatuts;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 *
 * @author ragab
 */
public class RXSubject {

    public static void main(String[] args) {
//        createSubject();
        useSubjectAsObservableAndObserver();
        
    }
    // act as Observable

    static void createSubject() {
        Subject<String> subject = PublishSubject.create();
        /**
         * this example of code running on rxjava2 but to make this code run on
         * rxjava you have to change observer to subscriber and the code will be
         * like below
         *
         * subject.subscribe(new Subscriber<String>(){ // and here you have to
         * implement the method of subscriber interface }
         *  
         * here also in rxjava create subject will be like below
         * Subject<String,String> subject ...
         *
         * in this example subject act as observable
         */
        subject.subscribe(new Observer<String>() {
            @Override
            public void onNext(String t) {
                System.out.println("onNext :" + t);
            }

            @Override
            public void onError(Throwable thrwbl) {
                System.out.println("onError");
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }

            @Override
            public void onSubscribe(Disposable dspsbl) {
                System.out.println("onSubscribe");
            }
        });

        subject.onNext("item 1");
        subject.onNext("item 2");
        subject.onNext("item 3");
        subject.onNext("item 4");
        subject.onComplete();
    }

    static void useSubjectAsObservableAndObserver() {
        Observable<Long> interval = Observable.interval(1, TimeUnit.SECONDS);
        Subject<Long> subject = PublishSubject.create();
        interval.subscribe(subject);
        subject.subscribe(
                item -> System.out.println("first sequence onNext:"+ item),
                t -> System.out.println(""+t.getMessage()),
                ()-> System.out.println("first sequence onComplete")
        );
        subject.subscribe(
                item -> System.out.println("second sequence onNext:"+ item),
                t -> System.out.println(""+t.getMessage()),
                ()-> System.out.println("second sequence onComplete")
        );
        subject.subscribe(
                item -> System.out.println("third sequence onNext:"+ item),
                t -> System.out.println(""+t.getMessage()),
                ()-> System.out.println("third sequence onComplete")
        );
        
        subject.onNext(1L);
        subject.onNext(2L);
        subject.onNext(3L);
        subject.onNext(4L);
        subject.onComplete();
    }
    

}
