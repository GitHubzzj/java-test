package com.byedbl.byedbl.spring.annotation;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@ComponentScan
public @interface SuperComponentScan {

    String[] value() default {"com.byedbl.byedbl"};
}
