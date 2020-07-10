package com.hust.lw;

import com.hust.lw.task.AsyncTasks;
import com.hust.lw.task.FutureAsyncTasks;
import com.hust.lw.task.SelfPoolAsyncTasks;
import com.hust.lw.task.SyncTasks;
import javafx.concurrent.Task;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.concurrent.Future;

@SpringBootTest
class LwApplicationTests {

    @Autowired
    private SyncTasks syncTasks;

    @Autowired
    private AsyncTasks asyncTasks;

    @Autowired
    private FutureAsyncTasks futureAsyncTasks;

    @Autowired
    private SelfPoolAsyncTasks selfPoolAsyncTasks;

    @Test
    void contextLoads() {
    }

    @Test
    public void testSyncTasks() throws Exception{
        syncTasks.doTaskOne();
        syncTasks.doTaskTwo();
        syncTasks.doTaskThree();
    }

    @Test
    public void testAsyncTasks() throws Exception{
        asyncTasks.doTaskOne();
        asyncTasks.doTaskTwo();
        asyncTasks.doTaskThree();

        try{
            Thread.sleep(1000000);}
        catch(Exception e){
            System.out.println("exception message info " + e.getMessage());
        }
    }

    @Test
    public void testFutureAsyncTasks() throws Exception{
        Future<String>  task1 = futureAsyncTasks.doTaskOne();
        Future<String>  task2 = futureAsyncTasks.doTaskTwo();
        Future<String>  task3 = futureAsyncTasks.doTaskThree();

        do{
            if(task1.isDone() && task2.isDone() && task3.isDone()){
                System.out.println("Three futureAsyncTasks finish totally");
                System.out.println("futureAsyncTask result[1-2-3]:" + task1.get() + "-" + task2.get() + "-" + task3.get());
                break;
            }

            try{
                Thread.sleep(1000);}
            catch(Exception e){
                System.out.println("exception message info " + e.getMessage());
            }
        }
        while(true);
    }

    @Test
    public void testSelfPoolAsyncTasks() throws Exception{
        selfPoolAsyncTasks.doTaskOne();
        selfPoolAsyncTasks.doTaskTwo();
        selfPoolAsyncTasks.doTaskThree();

        try{
            Thread.sleep(1000000);}
        catch(Exception e){
            System.out.println("exception message info " + e.getMessage());
        }
    }
}
