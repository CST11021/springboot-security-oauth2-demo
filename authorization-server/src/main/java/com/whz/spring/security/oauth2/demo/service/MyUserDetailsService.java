package com.whz.spring.security.oauth2.demo.service;

import com.whz.spring.security.oauth2.demo.domain.MyUser;
import com.whz.spring.security.oauth2.demo.repository.entity.SysPermission;
import com.whz.spring.security.oauth2.demo.repository.entity.SysUser;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private MyUserService myUserService;

    @Resource
    private MyPermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = myUserService.getByUsername(username);
        if (null == sysUser) {
            throw new UsernameNotFoundException(username);
        }

        // 获取用户权限
        List<SysPermission> permissionList = permissionService.findByUserId(sysUser.getId());
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(permissionList)) {
            for (SysPermission sysPermission : permissionList) {
                authorityList.add(new SimpleGrantedAuthority(sysPermission.getCode()));
            }
        }

        MyUser myUser = new MyUser(sysUser.getUsername(), passwordEncoder.encode(sysUser.getPassword()), authorityList);
        myUser.setId(sysUser.getId());

        return myUser;
    }
}
