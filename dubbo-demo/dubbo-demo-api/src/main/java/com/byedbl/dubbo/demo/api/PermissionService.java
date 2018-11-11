package com.byedbl.dubbo.demo.api;

import java.util.List;


/**
 *
 */
public interface PermissionService {
    List<String> getPermissions(Long id);
}
