package com.whz.spring.security.oauth2.demo.service.impl;

import com.whz.spring.security.oauth2.demo.entity.SysPermission;
import com.whz.spring.security.oauth2.demo.entity.SysRolePermission;
import com.whz.spring.security.oauth2.demo.entity.SysUserRole;
import com.whz.spring.security.oauth2.demo.repository.SysPermissionRepository;
import com.whz.spring.security.oauth2.demo.repository.SysRolePermissionRepository;
import com.whz.spring.security.oauth2.demo.repository.SysUserRoleRepository;
import com.whz.spring.security.oauth2.demo.service.MyPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyPermissionServiceImpl implements MyPermissionService {
    @Autowired
    private SysUserRoleRepository sysUserRoleRepository;
    @Autowired
    private SysRolePermissionRepository sysRolePermissionRepository;
    @Autowired
    private SysPermissionRepository sysPermissionRepository;

    @Override
    public List<SysPermission> findByUserId(Integer userId) {
        List<SysUserRole> sysUserRoleList = sysUserRoleRepository.findByUserId(userId);
        if (CollectionUtils.isEmpty(sysUserRoleList)) {
            return null;
        }
        List<Integer> roleIdList = sysUserRoleList.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
        List<SysRolePermission> rolePermissionList = sysRolePermissionRepository.findByRoleIds(roleIdList);
        if (CollectionUtils.isEmpty(rolePermissionList)) {
            return null;
        }
        List<Integer> permissionIdList = rolePermissionList.stream().map(SysRolePermission::getPermissionId).distinct().collect(Collectors.toList());
        List<SysPermission> sysPermissionList = sysPermissionRepository.findByIds(permissionIdList);
        if (CollectionUtils.isEmpty(sysPermissionList)) {
            return null;
        }
        return sysPermissionList;
    }
}
