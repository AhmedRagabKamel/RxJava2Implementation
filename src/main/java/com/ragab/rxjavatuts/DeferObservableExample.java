/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ragab.rxjavatuts;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 *
 * @author ragab
 */
public class DeferObservableExample {

    public static void main(String[] args) {
        DeferObservableExample instance  = new DeferObservableExample();
        instance.process();
    }

    void process() {
        final Person person = new Person();
//          person.setName("Ahmed Ragab");
//        person.setAge(26);
        Observable<String> nameObservable = Observable.just(person.getName());
        Observable<Integer> ageObservable = Observable.just(person.getAge());
        
        person.setName("Ahmed Ragab");
        person.setAge(26);
        
        nameObservable.subscribe( item -> System.out.println("name is :"+item),
                throwable ->System.out.println(""+throwable.getMessage()),
                ()-> System.out.println("on complete called ")
        );
        ageObservable.subscribe(item -> System.out.println("age is :"+item),
                throwable ->System.out.println(""+throwable.getMessage()),
                ()-> System.out.println("on complete called ")
        );
        
        
    }

    class Person {

        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

    }
}
