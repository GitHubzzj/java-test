package com.byedbl.byedbl.spring.condition;

import com.byedbl.byedbl.spring.condition.config.ConditionConfig;
import com.byedbl.byedbl.spring.condition.service.ListService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConditonMain {


    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConditionConfig.class);
        ListService listService = applicationContext.getBean(ListService.class);
        System.out.println(applicationContext.getEnvironment().getProperty("os.name") + " 的查询列表命名为: " + listService.showListCmd());
    }
}
