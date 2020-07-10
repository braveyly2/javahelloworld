package com.hust.lw.task;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class SyncTasks {
    public static Random random = new Random();

    public void doTaskOne() throws Exception{
        System.out.println("Start to do task 1");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        System.out.println("Finish task 1, total time:" + (end - start) + "micro seconds");
    }

    public void doTaskTwo() throws Exception{
        System.out.println("Start to do task 2");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        System.out.println("Finish task 2, total time:" + (end - start) + "micro seconds");
    }

    public void doTaskThree() throws Exception{
        System.out.println("Start to do task 3");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        System.out.println("Finish task 3, total time:" + (end - start) + "micro seconds");
    }
}
