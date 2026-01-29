package com.evergreen.EvergreenAuthServer.collections;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.evergreen.EvergreenAuthServer.collections.example_classes.ExampleStudent;
import com.evergreen.EvergreenAuthServer.collections.example_classes.ExampleStudentBuilder;

public class SampleFilter {

    /**
     * Filter method takes predicate a perdicate is a method that takes one argument and returns a value
     * ForEach method takes a consumer as an argument the conusumer funttions consumes the argument but
     * does not returns anything
     * 
     * @return
     */

    public List<ExampleStudent> getStudents() {
        ExampleStudent student1 = new ExampleStudentBuilder().setId(1).setName("alan").setMarks(90).build();
        ExampleStudent student2 = new ExampleStudentBuilder().setId(2).setName("bob").setMarks(40).build();
        ExampleStudent student3 = new ExampleStudentBuilder().setId(3).setName("calum").setMarks(60).build();

        List<ExampleStudent> studentsList = Arrays.asList(student1, student2, student3);
        return studentsList;
    }

    public void filterPassedStudent() {
        List<ExampleStudent> studentsList = this.getStudents();
        List<ExampleStudent> passedFiltertedStudents =
                studentsList.stream().filter((student) -> student.getMarks() >= 50).collect(Collectors.toList());
        for (ExampleStudent student : passedFiltertedStudents) {
            System.out.println("Student id = " + student.getId() + " name = " + student.getName() + " marks = " + student.getMarks());
        }
    }


    public void filterPassedStudentAndForEach() {
        List<ExampleStudent> studentsList = this.getStudents();
        studentsList.stream().filter((student) -> student.getMarks() >= 50).forEach(
                student -> System.out.println("Student id = " + student.getId() + " name = " + student.getName() + " marks = " + student.getMarks()));

    }


}
