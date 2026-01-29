package com.evergreen.EvergreenAuthServer.collections.example_classes;

public class ExampleStudentBuilder {

    private int id;
    private String name;
    private int marks;

    public ExampleStudentBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public ExampleStudentBuilder setName(String name) {
        this.name = name;
        return this;

    }

    public ExampleStudentBuilder setMarks(int marks) {
        this.marks = marks;
        return this;

    }


    public ExampleStudent build() {
        ExampleStudent exampleStudent = new ExampleStudent();
        exampleStudent.setId(id);
        exampleStudent.setName(name);
        exampleStudent.setMarks(marks);
        return exampleStudent;
    }



}


/// ExampleStudent student=new ExampleStudentBuilder.set
