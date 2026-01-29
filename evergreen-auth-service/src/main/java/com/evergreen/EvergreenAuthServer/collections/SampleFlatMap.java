package com.evergreen.EvergreenAuthServer.collections;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.evergreen.EvergreenAuthServer.collections.example_classes.ExampleStudent;
import com.evergreen.EvergreenAuthServer.collections.example_classes.ExampleStudentBuilder;

public class SampleFlatMap {


    public void show() {
        List<Integer> list1 = Arrays.asList(1, 2);
        List<Integer> list2 = Arrays.asList(3, 4);
        List<Integer> list3 = Arrays.asList(5, 6);

        List<List<Integer>> combinedList = Arrays.asList(list1, list2, list3);
        List<Integer> flatList = combinedList.stream().flatMap(list -> list.stream()).collect(Collectors.toList());
        System.out.println("combinedList => " + combinedList);
        System.out.println("flatList => " + flatList);

        ExampleStudent studendt1 = new ExampleStudentBuilder().setId(1).setName("alan").setMarks(80).build();
        ExampleStudent studendt2 = new ExampleStudentBuilder().setId(2).setName("bob").setMarks(80).build();
        List<ExampleStudent> studentList1 = Arrays.asList(studendt1, studendt2);

        ExampleStudent studendt3 = new ExampleStudentBuilder().setId(3).setName("calum").setMarks(80).build();
        ExampleStudent studendt4 = new ExampleStudentBuilder().setId(4).setName("darke").setMarks(80).build();
        List<ExampleStudent> studentList2 = Arrays.asList(studendt3, studendt4);

        ExampleStudent studendt5 = new ExampleStudentBuilder().setId(3).setName("emily").setMarks(80).build();
        ExampleStudent studendt6 = new ExampleStudentBuilder().setId(4).setName("fawad").setMarks(80).build();
        List<ExampleStudent> studentList3 = Arrays.asList(studendt5, studendt6);

        List<List<ExampleStudent>> combinedStudentsList = Arrays.asList(studentList1, studentList2, studentList3);

        System.out.println("combinedStudentsList " + combinedStudentsList);

        List<ExampleStudent> flattendStudentList =
                combinedStudentsList.stream().flatMap(studentList -> studentList.stream()).collect(Collectors.toList());

        System.out.println("flattendStudentList " + flattendStudentList);
    }

}
