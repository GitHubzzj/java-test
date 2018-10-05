package com.byedbl.byedbl.spring.schedule.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan("com.byedbl.byedbl")
@EnableScheduling
public class TaskScheduleConfig {
}
