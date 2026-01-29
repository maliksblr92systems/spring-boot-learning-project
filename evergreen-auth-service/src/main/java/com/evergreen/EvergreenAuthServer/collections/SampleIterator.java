package com.evergreen.EvergreenAuthServer.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class SampleIterator {

    public void exampleIterator() {
        // `List.of(1, 2, 3)` always returns an **immutable** list.
        // You cannot add or remove elements from it.
        //
        // `Iterator` allows us to safely mutate a list while iterating over it,
        // and the reference will be updated at runtime.
        //
        // `ListIterator` is another helper interface that provides additional
        // capabilities such as `hasPrevious()`, `hasNext()`, and other more
        // powerful methods for bidirectional traversal and modification.
        //
        // This is different from `CopyOnWriteArrayList`, where the list is not
        // mutated immediately during iteration; instead, a copy is created and
        // modifications are applied only after the iteration completes.



        List<Integer> numberList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));
        System.out.println("orignal list before " + numberList);

        Iterator<Integer> numberListIterator = numberList.iterator();

        while (numberListIterator.hasNext()) {
            Integer number = numberListIterator.next();
            System.out.println("number => " + number);
            if (number % 2 == 0) {
                numberListIterator.remove();
            }
        }
        System.out.println("orignal list after " + numberList);

    }

    public void exampleListIterator() {
        System.out.println("==================================================");
        System.out.println("exampleListIterator");
        System.out.println("==================================================");

        List<Integer> numbersList = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6));
        // here numberList.size() will set the cursor to the last object and then you can iterate backwards
        // this is optional
        ListIterator<Integer> numbersListIterator = numbersList.listIterator(numbersList.size());
        while (numbersListIterator.hasPrevious()) {
            System.out.println("element = " + numbersListIterator.previous());
        }
    }
}
