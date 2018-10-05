package com.byedbl.byedbl.spring.task;

import com.byedbl.byedbl.spring.task.config.TaskExecutorConfig;
import com.byedbl.byedbl.spring.task.service.AsyncTaskService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author root
 */
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(TaskExecutorConfig.class);
        AsyncTaskService taskService = applicationContext.getBean(AsyncTaskService.class);
        for(int i=0;i<10;i++) {
            taskService.executeAsyncTask(i);
            taskService.executeAsyncTaskPlus(i);
        }

    }
}
