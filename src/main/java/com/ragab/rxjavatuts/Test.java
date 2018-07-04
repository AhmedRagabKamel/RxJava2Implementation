/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ragab.rxjavatuts;

/**
 *
 * @author ragab
 */
public class Test {
    
    
    public static void main(String[] args) {
        ReactiveArrayList<String> list = new ReactiveArrayList<>();
        
        list.observeItemsAdded().subscribe(
                item -> System.out.println("on next item added :" + item),
                t -> System.out.println("on error item added :" + t.getMessage()),
                () -> System.out.println("on complete add :" )
        );
        list.observeItemsRemoved().subscribe(
                item -> System.out.println("on next item removed :" + item),
                t -> System.out.println("on error item remove :" + t.getMessage()),
                () -> System.out.println("on complete remove :" )
        );
        
        
        list.add("ahmed");
        list.add("ragab");
        list.add("android");
        list.add("developer");
        
        list.remove("ragab");
        
        list.stream().forEach(item -> System.out.println(item));
    }
    
}
