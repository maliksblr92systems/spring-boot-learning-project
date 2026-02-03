package com.evergreen.EvergreenAuthServer.collections;

import java.util.Arrays;
import java.util.List;

import com.evergreen.EvergreenAuthServer.collections.example_classes.ExampleStudent;
import com.evergreen.EvergreenAuthServer.collections.example_classes.ExampleStudentBuilder;

public class SampleMap {

    public List<String> getThingNames() {
        List<String> names = Arrays.asList("bus", "car", "mototrcycle", "bycycle");
        return names;
    }



    public List<ExampleStudent> getStudents() {
        ExampleStudent student1 = new ExampleStudentBuilder().setId(1).setName("alan").setMarks(90).build();
        ExampleStudent student2 = new ExampleStudentBuilder().setId(2).setName("bob").setMarks(40).build();
        ExampleStudent student3 = new ExampleStudentBuilder().setId(3).setName("calum").setMarks(60).build();

        List<ExampleStudent> studentsList = Arrays.asList(student1, student2, student3);
        return studentsList;
    }

    /**
     * substring returns the remaining string from the begin index provided as argument
     */
    public void convertNamesFirstLetterToUpperCaseAndPrint() {
        List<String> thingNames = this.getThingNames();
        thingNames.stream().map(thing -> Character.toUpperCase(thing.charAt(0)) + thing.substring(1)).forEach((thing) -> System.out.println("thing is " + thing));
    }

    private ExampleStudent changeNameCase(ExampleStudent student) {
        String name = student.getName();
        String capitalCaseName = Character.toUpperCase(name.charAt(0)) + name.substring(1);
        student.setName(capitalCaseName);
        return student;

    }

    public void convertStudentNamesToCapitalCaseAndPrint() {
        List<ExampleStudent> students = this.getStudents();
        // students.stream().map(p -> changeNameCase(p)).forEach(p -> System.out.println("Modified Name is =
        // " + p.getName()));
        students.stream().map(student -> {
            student.setName(Character.toUpperCase(student.getName().charAt(0)) + student.getName().substring(1));
            return student;
        }).forEach((student) -> System.out.println("student is " + student));

    }

    public void sortStudentByMarks() {
        List<ExampleStudent> students = this.getStudents();
        System.out.println("====== STUDENTS BEFORE SORT =======");
        students.forEach(student -> System.out.println(student));
        System.out.println("====== STUDENTS AFTER SORT =======");
        students.stream().sorted((student1, student2) -> student1.getMarks() - student2.getMarks()).forEach(student -> System.out.println(student));
    }

}
