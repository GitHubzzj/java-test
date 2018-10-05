package com.byedbl.task.method3jdkservice;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TestScheduledExecutorService {

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {


                System.out.println(Thread.currentThread().getName()+"  ScheduledExecutorService Task is called! "+System.currentTimeMillis());
                for(long i=0;i< 10000000000L;i++) {

                }
//                try {
//                    Thread.sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                System.out.println("over..........."+System.currentTimeMillis());
            }
        };
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        // 参数：1、任务体 2、首次执行的延时时间
        //      3、任务执行间隔 4、0间隔时间单位
        //下一次任务不以上一次任务为基准,要考虑并发情况
        service.scheduleAtFixedRate(runnable, 0, 3, TimeUnit.SECONDS);
        //下一次的任务以上一次任务为基准
//        service.scheduleWithFixedDelay(runnable, 0, 3, TimeUnit.SECONDS);
    }

}
