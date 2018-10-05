package com.byedbl.byedbl.spring.condition.service;

public class WindowsListService implements ListService {
    @Override
    public String showListCmd() {
        return "dir";
    }
}
