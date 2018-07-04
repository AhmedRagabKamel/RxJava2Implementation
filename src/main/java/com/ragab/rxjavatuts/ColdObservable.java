/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ragab.rxjavatuts;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ragab
 */
public class ColdObservable {

    public static void main(String[] args) throws InterruptedException {
        method1();
        Thread.sleep(2000);
        method2();
     
    }

    //imperative programming
    static void method1() {
        Observable<Integer> integerObservable = Observable.fromArray(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                // this method called at the first of emittion
                System.out.println("onSubscribe");
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("next number is : " + integer);
            }

            @Override
            public void onError(Throwable e) {
                // this method called if an error happened and it stop or cancel the sequence of process
                System.out.println("an error happen");
            }

            @Override
            public void onComplete() {
                // called at the end of emittion
                System.out.println("oncomplete method called");
            }
        };

        integerObservable.subscribe(observer);
    }

    // functional programming
    static void method2() {
        Disposable disposable = Observable.fromArray(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).subscribe(
                integer -> System.out.println(integer), // this represent method onNext()
                t -> System.out.println(t.getMessage()), // this represent method onError()
                () -> System.out.println("oncomplete called") // this represent method onComplete()
        );

        disposable.dispose();
        // another way to create observable 
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).timer(20,TimeUnit.SECONDS).subscribe(
                integer -> System.out.println(integer), // this represent method onNext()
                t -> System.out.println(t.getMessage()), // this represent method onError()
                () -> System.out.println("oncomplete called") // this represent method onComplete()
        );
        
        
        
        
    }
}
