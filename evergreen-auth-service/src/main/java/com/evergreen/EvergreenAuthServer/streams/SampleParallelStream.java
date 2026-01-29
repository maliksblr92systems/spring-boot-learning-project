package com.evergreen.EvergreenAuthServer.streams;

import java.util.stream.IntStream;

public class SampleParallelStream {

    private static int TIMES = 5;

    /**
     * parallel streams use "Fork Join Framework" they are best when you have pretty heavy job so that
     * it can be break down into peaces but if jobs are less heavy the multiples thread creation and
     * managing overhead actually decreases the performance unlike sequential stream the parallel
     * streams breaks the streams into multiple chunks and they are prcossed on multiple cores instead
     * of multiple threads in the single core
     * 
     * To determine which stream to use we use 'NQ MODEL' where N is the # of elements where Q is the #
     * of computations per element The product of NQ is greater use parallel streams otherwise
     * sequential streams
     * 
     * 
     * List.of("one").stream().parallel() gurantees return of parallel stream
     * List.of("onw").parallelStream() does not gurantees return of parallel stream you have util
     * methods isParallel to check if it
     */
    public void exampleWhereParallelStreamMayHelp() {
        for (int x = 0; x < TIMES; x++) {
            var startTime = System.currentTimeMillis();
            long total = IntStream.range(1, 100_000_000).parallel().sum();
            var endTime = System.currentTimeMillis();
            var totalTimeTaken = endTime - startTime;
            System.out.println("total time taken = " + totalTimeTaken + " total is " + total);
        }

    }

    public void exampleWhereParallelStreamMayCauseConcurrenyIssues() {
        int sumSeuentialStream = IntStream.rangeClosed(0, 10).reduce(100, Integer::sum); // gives 55
        int sumParallelStream = IntStream.rangeClosed(0, 10).parallel().reduce(100, Integer::sum); // gives 1155

        System.out.println("=========================================");
        System.out.println("sumSeuentialStream => " + sumSeuentialStream);
        System.out.println("sumParallelStream => " + sumParallelStream);
        System.out.println("=========================================");


        // Key point (this is NOT a concurrency issue
        // Rule for parallel stream is below
        // In parallel streams, the identity must be a neutral value
        // idientity can be nuetral means 0
        // identity can be associative means in case of multiplication can be 1
        // idintity can be statles means incase of string ""

        int sumSeuentialStreamCorrect = IntStream.rangeClosed(0, 10).reduce(0, Integer::sum); // gives 55
        int sumParallelStreamCorrect = IntStream.rangeClosed(0, 10).parallel().reduce(0, Integer::sum); // gives 1155
        System.out.println("=========================================");
        System.out.println("sumSeuentialStreamCorrect => " + sumSeuentialStreamCorrect);
        System.out.println("sumParallelStreamCorrect => " + sumParallelStreamCorrect);
        System.out.println("=========================================");



    }

}
