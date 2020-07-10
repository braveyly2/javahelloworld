package com.hust.lw.task;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class SelfPoolAsyncTasks {
    public static Random random = new Random();

    @Async("taskExecutor")
    public void doTaskOne() throws Exception{
        System.out.println("Start to do task 1");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        System.out.println("Finish task 1, total time:" + (end - start) + "micro seconds" + "  current thread name:" + Thread.currentThread().getName() );
    }

    @Async("taskExecutor")
    public void doTaskTwo() throws Exception{
        System.out.println("Start to do task 2");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        System.out.println("Finish task 2, total time:" + (end - start) + "micro seconds" + "  current thread name:" + Thread.currentThread().getName());
    }

    @Async("taskExecutor")
    public void doTaskThree() throws Exception{
        System.out.println("Start to do task 3");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        System.out.println("Finish task 3, total time:" + (end - start) + "micro seconds" + "  current thread name:" + Thread.currentThread().getName());
    }
}
