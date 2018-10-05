package com.byedbl.byedbl.spring.task.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncTaskService {

    @Async
    public void executeAsyncTask(Integer i) {
        System.out.println("executeAsyncTask执行异步任务" + i);

    }

    @Async
    public void executeAsyncTaskPlus(Integer i) {
        System.out.println("executeAsyncTaskPlus执行异步任务" + (i+1));
    }
}
