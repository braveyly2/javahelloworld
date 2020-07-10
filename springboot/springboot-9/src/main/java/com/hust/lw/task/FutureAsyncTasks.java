package com.hust.lw.task;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.Future;

@Component
public class FutureAsyncTasks {
    public static Random random = new Random();

    @Async
    public Future<String> doTaskOne() throws Exception{
        System.out.println("Start to do FutureAsyncTask 1");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        System.out.println("Finish FutureAsyncTask 1, total time:" + (end - start) + "micro seconds");
        return new AsyncResult<>("FutureAsyncTask 1 done");
    }

    @Async
    public Future<String> doTaskTwo() throws Exception{
        System.out.println("Start to do FutureAsyncTask 2");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        System.out.println("Finish FutureAsyncTask 2, total time:" + (end - start) + "micro seconds");
        return new AsyncResult<>("FutureAsyncTask 2 done");
    }

    @Async
    public Future<String> doTaskThree() throws Exception{
        System.out.println("Start to do FutureAsyncTask 3");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        System.out.println("Finish FutureAsyncTask 3, total time:" + (end - start) + "micro seconds");
        return new AsyncResult<>("FutureAsyncTask 3 done");
    }
}
