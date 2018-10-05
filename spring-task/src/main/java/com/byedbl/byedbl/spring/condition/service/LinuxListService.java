package com.byedbl.byedbl.spring.condition.service;

public class LinuxListService implements ListService {
    @Override
    public String showListCmd() {
        return "ls";
    }
}
