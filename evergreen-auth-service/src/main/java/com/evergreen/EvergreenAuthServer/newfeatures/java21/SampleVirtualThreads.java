package com.evergreen.EvergreenAuthServer.newfeatures.java21;

public class SampleVirtualThreads {


    // When we create a thread using `new Thread(...)`, the JVM behind the scenes maps
    // the Java thread to an OS thread.
    // This mapping usually takes around 1 MB of memory.
    // This type of thread is called a **Platform Thread**.
    //
    // Traditionally, using platform threads, we can use multiple threads to achieve
    // parallel task computation.
    // However, if the number of threads increases to a very large number, the system
    // may not perform efficiently because:
    //
    // 1. Platform threads consume more memory.
    // 2. Platform threads are more CPU intensive.
    // 3. Platform threads cause overhead due to scheduling and context switching.
    // 4. Once a Java thread is mapped to a platform thread, the OS thread stays
    // mounted (bound/acquired) until the thread execution completes.
    //
    // Java 21 introduced **Virtual Threads**.
    //
    // They are extremely helpful because:
    //
    // 1. They are extremely lightweight.
    // 2. The JVM manages the lifecycle of virtual threads instead of the OS.
    // 3. Virtual threads are mounted to actual OS threads, and in case of long I/O tasks,
    // the JVM can unmount them and execute another waiting thread, improving performance.
    // 4. Because of mounting and unmounting, we can avoid writing asynchronous code
    // and achieve the same benefits using synchronous code.
    //
    // Where virtual threads are useful (use cases):
    //
    // 1. I/O operations (e.g., file reads).
    // 2. Handling a large number of requests.
    // 3. Database or network calls.
    //
    // Virtual threads negatively impact performance if used for:
    //
    // 1. CPU-intensive tasks, for example, image processing.


    public void demo() {
        Thread virtualThread = Thread.startVirtualThread(() -> {
            System.out.println("==============================");
            System.out.println("==============================");
            System.out.println("Thread Name" + Thread.currentThread().getName());
            System.out.println("==============================");

        });

        Thread platformThread = new Thread(() -> {
            System.out.println("==============================");
            System.out.println("==============================");
            System.out.println("Thread Name" + Thread.currentThread().getName());
            System.out.println("==============================");

        });
    }
}
