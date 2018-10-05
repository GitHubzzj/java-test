package com.byedbl.task.method4quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

public class TestQuartz {

    public static void main(String[] args) {
        try {
            //获取调度器
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            //创建任务器：定义任务细节
            JobDetail job = newJob(MyJob.class)
                    .withIdentity("job", "group").build();

            // 休眠时长可指定时间单位，此处使用秒作为单位（withIntervalInSeconds）
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("simpleTrigger", "simpleTriggerGroup").startNow()
                    .withSchedule(simpleSchedule().withIntervalInSeconds(3).repeatForever())
                    .build();

            scheduler.scheduleJob(job, trigger);

            // scheduler.shutdown();

        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }


}
