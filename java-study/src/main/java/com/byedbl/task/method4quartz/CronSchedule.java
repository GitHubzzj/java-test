package com.byedbl.task.method4quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.ParseException;

import static org.quartz.JobBuilder.newJob;

/**
 * 实现定时任务,设置某一个cron表达式
 */
public class CronSchedule {

    public static void start() throws SchedulerException, ParseException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        //InvokeStatJob是实现了org.quartz.Job的类
        JobDetail job = newJob(MyJob.class)
                .withIdentity("job", "group").build();
        CronExpression cexp = new CronExpression("0 0 * * * ?");
//        MutableTrigger cronTrigger = CronScheduleBuilder.cronSchedule(cexp).build();

        //作业的触发器
        CronTrigger cronTrigger = TriggerBuilder.//和之前的 SimpleTrigger 类似，现在的 CronTrigger 也是一个接口，通过 Tribuilder 的 build()方法来实例化
                newTrigger().
                withIdentity("cronTrigger", "cronTrigger").
                withSchedule(CronScheduleBuilder.cronSchedule("0 25 13 * * ?")). //在任务调度器中，使用任务调度器的 CronScheduleBuilder 来生成一个具体的 CronTrigger 对象
                build();

        scheduler.scheduleJob(job, cronTrigger);
        scheduler.start();
    }

    public static void main(String[] args) {
        try {
            start();
        } catch (SchedulerException | ParseException e) {
            e.printStackTrace();
        }
    }
}
