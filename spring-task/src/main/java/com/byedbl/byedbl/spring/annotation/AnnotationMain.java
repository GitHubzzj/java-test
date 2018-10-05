package com.byedbl.byedbl.spring.annotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AnnotationMain {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SuperConfig.class);
        DemoService demoService = context.getBean(DemoService.class);
        demoService.outputResult();
    }
}
