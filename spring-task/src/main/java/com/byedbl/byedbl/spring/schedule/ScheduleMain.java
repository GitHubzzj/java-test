package com.byedbl.byedbl.spring.schedule;

import com.byedbl.byedbl.spring.schedule.config.TaskScheduleConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ScheduleMain {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(TaskScheduleConfig.class);

    }
}
