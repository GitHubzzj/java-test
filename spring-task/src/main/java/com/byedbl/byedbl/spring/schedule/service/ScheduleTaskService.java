package com.byedbl.byedbl.spring.schedule.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ScheduleTaskService {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        System.out.println("每隔5秒 执行一次: "+ dateFormat.format(new Date()));
    }


    @Scheduled(cron = "50 19 21 ? * *")
    public void fixTimeExecution() {
        System.out.println("在指定时间执行" + dateFormat.format(new Date()) +  "执行");
    }
}
