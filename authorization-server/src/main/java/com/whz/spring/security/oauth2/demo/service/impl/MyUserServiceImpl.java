package com.whz.spring.security.oauth2.demo.service.impl;

import com.whz.spring.security.oauth2.demo.entity.SysUser;
import com.whz.spring.security.oauth2.demo.repository.SysUserRepository;
import com.whz.spring.security.oauth2.demo.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyUserServiceImpl implements MyUserService {

    @Autowired
    private SysUserRepository sysUserRepository;

    @Override
    public SysUser getByUsername(String username) {
        return sysUserRepository.findByUsername(username);
    }
}
