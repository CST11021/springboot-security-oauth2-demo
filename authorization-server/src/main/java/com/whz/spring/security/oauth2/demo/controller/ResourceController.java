package com.whz.spring.security.oauth2.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

/**
 * @Author 盖伦
 * @Date 2024/7/7
 */
@Controller
public class ResourceController {

    @ResponseBody
    @GetMapping("/getRes")
    public String getRes() {
        return "res";
    }

    @ResponseBody
    @GetMapping("/info")
    public Principal info(Principal principal) {
        return principal;
    }

    @ResponseBody
    @GetMapping("/me")
    public Authentication me(Authentication authentication) {
        return authentication;
    }

}
