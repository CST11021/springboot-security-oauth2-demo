package com.whz.spring.security.oauth2.demo.service;

import com.whz.spring.security.oauth2.demo.repository.entity.SysPermission;

import java.util.List;


public interface MyPermissionService {
    List<SysPermission> findByUserId(Integer userId);
}
