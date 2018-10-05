package com.byedbl.byedbl.spring.annotation;

import org.springframework.stereotype.Service;

@Service
public class DemoService {

    public void outputResult() {
        System.out.println("从组合注解中获取 bean ");
    }
}
