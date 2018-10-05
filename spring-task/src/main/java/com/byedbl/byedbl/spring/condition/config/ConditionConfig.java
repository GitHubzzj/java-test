package com.byedbl.byedbl.spring.condition.config;

import com.byedbl.byedbl.spring.condition.condition.LinuxCondition;
import com.byedbl.byedbl.spring.condition.condition.WindowsCondition;
import com.byedbl.byedbl.spring.condition.service.LinuxListService;
import com.byedbl.byedbl.spring.condition.service.ListService;
import com.byedbl.byedbl.spring.condition.service.WindowsListService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConditionConfig {

    @Bean
    @Conditional(WindowsCondition.class)
    public ListService windowsListService() {
        return new WindowsListService();
    }

    @Bean
    @Conditional(LinuxCondition.class)
    public ListService linuxListService() {
        return new LinuxListService();
    }

}
