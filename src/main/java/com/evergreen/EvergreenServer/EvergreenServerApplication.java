package com.evergreen.EvergreenServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import com.evergreen.EvergreenServer.newfeatures.java21.SampleVirtualThreads;

@SpringBootApplication
@EnableAspectJAutoProxy
public class EvergreenServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EvergreenServerApplication.class, args);
        SampleVirtualThreads sampleVirtualThreads = new SampleVirtualThreads();
        sampleVirtualThreads.demo();
    }



}
