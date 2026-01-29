package com.evergreen.EvergreenAuthServer.utils.comparators;

import java.util.Comparator;

public class GenericNumericComparator implements Comparator<Integer> {
    @Override
    public int compare(Integer integer1, Integer integer2) {
        System.out.println("=================================");
        System.out.println("Integer a "+integer1);
        System.out.println("Integer b "+integer2);
        System.out.println("=================================");
        return integer1-integer2;
    }
}
