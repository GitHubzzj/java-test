package com.byedbl.dubbo.demo.provider;

import com.byedbl.dubbo.demo.api.PermissionService;

import java.util.ArrayList;
import java.util.List;

public class PermissionServiceImpl implements PermissionService {
    @Override
    public List<String> getPermissions(Long id) {
        List<String> demo = new ArrayList<String>();
        demo.add(String.format("Permission_%d", id - 1));
        demo.add(String.format("Permission_%d", id));
        demo.add(String.format("Permission_%d", id + 1));
        return demo;
    }
}
