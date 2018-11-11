package com.byedbl.dubbo.demo.consumer;

import com.byedbl.dubbo.demo.api.PermissionService;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class DubboConsumerMain {
    public static void main(String[] args) {

        //测试常规服务
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("consumer.xml");
        context.start();
        System.out.println("consumer2 start");
        PermissionService demoService = context.getBean(PermissionService.class);
        System.out.println("consumer2");
        System.out.println(demoService.getPermissions(1L));
    }
}
