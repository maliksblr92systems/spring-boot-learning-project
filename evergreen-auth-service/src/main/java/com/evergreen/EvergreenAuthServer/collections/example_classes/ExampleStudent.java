package com.evergreen.EvergreenAuthServer.collections.example_classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExampleStudent {

    private int id;
    private String name;
    private int marks;


}
