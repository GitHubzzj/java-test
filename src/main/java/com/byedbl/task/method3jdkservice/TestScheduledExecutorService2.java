package com.byedbl.task.method3jdkservice;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TestScheduledExecutorService2 {

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"  ScheduledExecutorService Task is called! "+System.currentTimeMillis());
            }
        };
        ThreadFactory threadFactory = new CustomThreadFactory();
        ScheduledExecutorService service = new ScheduledThreadPoolExecutor(1,threadFactory);
        // 参数：1、任务体 2、首次执行的延时时间
        //      3、任务执行间隔 4、间隔时间单位
        service.scheduleAtFixedRate(runnable, 3, 3, TimeUnit.SECONDS);
    }

    private static class CustomThreadFactory implements ThreadFactory {

        private AtomicInteger count = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            String threadName = TestScheduledExecutorService2.class.getSimpleName() + count.addAndGet(1);
            t.setName(threadName);
            return t;
        }
    }
}
