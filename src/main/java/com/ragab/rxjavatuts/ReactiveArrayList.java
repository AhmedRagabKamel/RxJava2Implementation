/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ragab.rxjavatuts;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author ragab
 */
class ReactiveArrayList<T> extends ArrayList<T> {

    private PublishSubject<T> addSubject
            = PublishSubject.create();
    private PublishSubject<Object> removeSubject
            = PublishSubject.create();

    @Override
    public boolean add(T item) {
        boolean result = super.add(item);
        if (result) {
            addSubject.onNext(item);
        }
        return result;
    }

    @Override
    public void add(int index, T item) {
        super.add(index, item);
        addSubject.onNext(item);
    }

    @Override
    public T remove(int index) {
        T removedItem = super.remove(index);
        removeSubject.onNext(removedItem);
        return removedItem;
    }

    @Override
    public boolean remove(Object object) {
        boolean result = super.remove(object);
        if (result) {
            removeSubject.onNext(object);
        }
        return result;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean result = super.addAll(c);
        if (result) {
            for (T t : c) {
                addSubject.onNext(t);
            }
        }
        return result;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        boolean result = super.addAll(index, c);
        if (result) {
            for (T t : c) {
                addSubject.onNext(t);
            }
        }
        return result;
    }

    public Observable<T> observeItemsAdded() {
//        return addSubject.asObservable();
        return addSubject.hide();
    }

    public Observable<Object> observeItemsRemoved() {
        return removeSubject.hide();
    }
}
