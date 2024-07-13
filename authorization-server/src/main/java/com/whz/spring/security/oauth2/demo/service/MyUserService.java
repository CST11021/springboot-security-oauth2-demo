package com.whz.spring.security.oauth2.demo.service;


import com.whz.spring.security.oauth2.demo.repository.entity.SysUser;

public interface MyUserService {
    SysUser getByUsername(String username);
}
