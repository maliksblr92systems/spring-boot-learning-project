package com.evergreen.EvergreenServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import com.evergreen.EvergreenServer.collections.SampleFilter;
import com.evergreen.EvergreenServer.collections.SampleFlatMap;
import com.evergreen.EvergreenServer.collections.SampleMap;

@SpringBootApplication
@EnableAspectJAutoProxy
public class EvergreenServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EvergreenServerApplication.class, args);

        // Filter examples
        SampleFilter sampleFilter = new SampleFilter();
        sampleFilter.filterPassedStudent();
        System.out.println("================================");
        sampleFilter.filterPassedStudentAndForEach();

        // Map examples
        SampleMap sampleMap = new SampleMap();
        sampleMap.convertNamesFirstLetterToUpperCaseAndPrint();
        sampleMap.convertStudentNamesToCapitalCaseAndPrint();
        sampleMap.sortStudentByMarks();


        // FlatMap
        SampleFlatMap sampleFlatMap = new SampleFlatMap();
        sampleFlatMap.show();


    }



}
