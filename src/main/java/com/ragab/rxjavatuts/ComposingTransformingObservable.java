/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ragab.rxjavatuts;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author ragab
 */
public class ComposingTransformingObservable {

    public static void main(String[] args) throws InterruptedException {
        ComposingTransformingObservable instance
                = new ComposingTransformingObservable();
//        instance.useMap();
//        instance.useFlatMap();
//        instance.useZip();
//        instance.useConcat();
        instance.useFilter();
//        instance.useDistincit();
//        instance.useFirstElement();
//        instance.useTake();
//        instance.useStartWith();
//        instance.useHandleError();
//        instance.useHandleErrorReturn();
//        instance.useRetry();
Thread.sleep(60000);
        instance.useRetryWhen2();
        // to get last element
        System.out.println(Observable.fromArray(1, 2, 4).last(Integer.SIZE).blockingGet());
    }

    public void useMap() {
        Observable.fromArray(1, 2, 3, 4, 5, 6).map(new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer t) throws Exception {
                return t * 10;
            }
        }).subscribe(number -> System.out.println("number is: " + number),
                t -> System.out.println(t.getMessage()),
                () -> System.out.println("oncomplete method called")
        );

        Observable
                .fromArray(1, 2, 3, 4, 5, 6)
                .map(item -> item * 10)
                .subscribe(number -> System.out.println("number is: " + number),
                        t -> System.out.println(t.getMessage()),
                        () -> System.out.println("oncomplete method called")
                );
    }

    public void useFlatMap() {
        //imperative way
        /**
         * in flat map returned object must be observable
         */
        Observable.fromArray(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .flatMap(new Function<Integer, Observable<String>>() {
                    @Override
                    public Observable<String> apply(Integer t) throws Exception {
                        return Observable.just("number is " + t);
                    }
                }).subscribe(numberAsString -> System.out.println(numberAsString),
                t -> System.out.println(t.getMessage()),
                () -> System.out.println("oncomplete method called"));

        // functional way
        Observable.fromArray(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .flatMap(number -> Observable.just("number as string is:" + number))
                .subscribe(
                        numberAsString -> System.out.println(numberAsString),
                        t -> System.out.println(t.getMessage()),
                        () -> System.out.println("oncomplete method called"));

    }

    public void useZip() {

        //imperative way of code
        Observable<Integer> major = Observable.range(1, 4);
        Observable<Integer> minor = Observable.range(5, 10);

        Observable.zip(minor, major, new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer t1, Integer t2) throws Exception {
                return t1 + t2;
            }
        }).subscribe(number -> System.out.println("number is: " + number),
                t -> System.out.println(t.getMessage()),
                () -> System.out.println("oncomplete method called"));

        //functional way 
        Observable.zip(
                minor, major, (n1, n2) -> n1 + n2
        )
                .subscribe(number -> System.out.println("number is: " + number),
                        t -> System.out.println(t.getMessage()),
                        () -> System.out.println("oncomplete method called"));
    }

    public void useConcat() {
        Observable<String> first = Observable.just("item1", "item2");
        Observable<String> second = Observable.just("item3", "item4");
        Observable<Integer> integerSecond = Observable.just(1, 2, 3);

        Observable.concat(first, second).subscribe(item -> System.out.println(item),
                t -> System.out.println(t.getMessage()),
                () -> System.out.println("oncomplete method called"));

        Observable.concat(first, integerSecond).subscribe(item -> System.out.println(item),
                t -> System.out.println(t.getMessage()),
                () -> System.out.println("oncomplete method called"));

        Observable.concat(first, integerSecond)
                .map((item) -> {
                    return (item instanceof Integer) ? Observable.just((int) item * 2) : Observable.just(item);
                })
                .subscribe(item -> System.out.println(item.blockingFirst()),
                        t -> System.out.println(t.getMessage()),
                        () -> System.out.println("oncomplete method called"));

    }

    public void useFilter() {

        // imperative way
        Observable.range(4, 20).filter(new Predicate<Integer>() {
            @Override
            public boolean test(Integer t) throws Exception {
                return t > 10;
            }
        }).subscribeOn(Schedulers.computation()).subscribe(item -> System.out.println(item),
                t -> System.out.println(t.getMessage()),
                () -> System.out.println("oncomplete method called"));
        // functional way
        Observable.range(4, 20)
                .filter(number -> number > 10)
                .subscribe(item -> System.out.println(item),
                        t -> System.out.println(t.getMessage()),
                        () -> System.out.println("oncomplete method called"));
    }

    public void useDistincit() {
        Observable.fromArray(1, 2, 2, 2, 3, 4, 4, 5, 5, 5, 5, 6, 6, 6, 8, 8, 7, 7, 7).distinct()
                .subscribe(item -> System.out.println(item),
                        t -> System.out.println(t.getMessage()),
                        () -> System.out.println("oncomplete method called"));
    }

    public void useFirstElement() {
        Observable.fromArray(1, 2, 2, 2, 3, 4, 4, 5, 5, 5, 5, 6, 6, 6, 8, 8, 7, 7, 7).firstElement()
                .subscribe(item -> System.out.println(item),
                        t -> System.out.println(t.getMessage()),
                        () -> System.out.println("oncomplete method called"));
    }

    public void useTake() {
        Observable.fromArray(1, 2, 2, 2, 3, 4, 4, 5, 5, 5, 5, 6, 6, 6, 8, 8, 7, 7, 7).take(3)
                .subscribe(item -> System.out.println(item),
                        t -> System.out.println(t.getMessage()),
                        () -> System.out.println("oncomplete method called"));
    }

    public void useStartWith() {
        Observable.fromArray(1, 2, 2, 2, 3, 4, 4, 5, 5, 5, 5, 6, 6, 6, 8, 8, 7, 7, 7).startWith(3)
                .subscribe(item -> System.out.println(item),
                        t -> System.out.println(t.getMessage()),
                        () -> System.out.println("oncomplete method called"));
    }

    public void useHandleError() {
        Observable.fromArray("122", "343", "s", "5").map(i -> Integer.parseInt(i))
                .onErrorResumeNext(Observable.<Integer>empty())
                .subscribe(item -> System.out.println(item),
                        t -> System.out.println(t.getMessage()),
                        () -> System.out.println("oncomplete method called"));
    }

    public void useHandleErrorReturn() {
        Observable.fromArray("122", "343", "s", "5").map(i -> Integer.parseInt(i))
                .onErrorReturn(new Function<Throwable, Integer>() {
                    @Override
                    public Integer apply(Throwable t) throws Exception {
                        return -1;
                    }
                })
                .subscribe(item -> System.out.println(item),
                        t -> System.out.println(t.getMessage()),
                        () -> System.out.println("oncomplete method called"));

        Observable.fromArray("122", "343", "s", "5").map(i -> Integer.parseInt(i))
                .onExceptionResumeNext(Observable.just(-1))
                .subscribe(item -> System.out.println(item),
                        t -> System.out.println(t.getMessage()),
                        () -> System.out.println("oncomplete method called"));
    }

    public void useRetry() {

        Observable.fromArray("122", "343", "s", "5").map(i -> Integer.parseInt(i))
                /* .retry() */ // this will make code run forever so use retry(1) 
                .retry(1)
                .subscribe(item -> System.out.println(item),
                        t -> System.out.println(t.getMessage()),
                        () -> System.out.println("oncomplete method called"));
    }

    public void useRetryWhen() {
        Observable.fromArray("122", "343", "s", "5").map(i -> Integer.parseInt(i))
                /* .retry() */ // this will make code run forever so use retry(1) 
                .retryWhen(new Function<Observable<? extends Throwable>, Observable<?>>() {
                    @Override
                    public Observable<?> apply(Observable<? extends Throwable> observable) throws Exception {
                        System.out.println("in retry when method ");
                        return Observable.timer(5, TimeUnit.SECONDS);
                    }
                }).subscribeOn(Schedulers.computation()).subscribe(item -> System.out.println(item),
                t -> System.out.println(t.getMessage()),
                () -> System.out.println("oncomplete method called"));
    }

    public void useRetryWhen2() {
        Observable.fromArray("122", "343", "s", "5").map(i -> Integer.parseInt(i))
                /* .retry() */ // this will make code run forever so use retry(1) 
                .retryWhen(new Function<Observable<? extends Throwable>, Observable<?>>() {
                    @Override
                    public Observable<?> apply(Observable<? extends Throwable> observable) {
                        return observable.zipWith(Observable.range(1, 3),
                                new BiFunction<Throwable, Integer, Integer>() {
                            @Override
                            public Integer apply(Throwable throwable,
                                    Integer retryCount) {
                                System.out.println("retry #" + retryCount);
                                return retryCount;
                            }
                        }).flatMap(new Function<Integer, Observable<?>>() {
                                    @Override
                                    public Observable<?> apply(Integer integer) {
                                        return Observable.timer(5, TimeUnit.SECONDS);
                                    }
                                });
                    }
                }).subscribeOn(Schedulers.computation()).subscribe(
                item -> System.out.println(item),
                        t -> System.out.println(t.getMessage()),
                        () -> System.out.println("oncomplete method called")
                );
    }
}
