package com.byedbl.task.method4quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MyJob   implements org.quartz.Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Quartz task is called!");
    }

}
