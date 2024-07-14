package com.whz.spring.security.oauth2.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

/**
 * @author ChengJianSheng
 * @date 2019-03-03
 */
@Controller
@RequestMapping
public class MemberController {

    @GetMapping({"/", "/index", "/member/list"})
    public String list() {
        return "member/index";
    }

    @GetMapping("/member/info")
    @ResponseBody
    public Principal info(Principal principal) {
        return principal;
    }

    @GetMapping("/member/me")
    @ResponseBody
    public Authentication me(Authentication authentication) {
        return authentication;
    }

    /**
     * 需要有'member:add'权限才可以访问
     *
     * @return
     */
    @PreAuthorize("hasAuthority('member:add')")
    @ResponseBody
    @GetMapping("/member/add")
    public String add() {
        return "有add权限";
    }

    /**
     * 需要有'member:detail'权限才可以访问
     *
     * @return
     */
    @PreAuthorize("hasAuthority('member:detail')")
    @ResponseBody
    @GetMapping("/member/detail")
    public String detail() {
        return "有detail权限";
    }

}
