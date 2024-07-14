package com.whz.spring.security.oauth2.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author 盖伦
 * @Date 2024/7/14
 */
@Controller
public class IndexController {

    @GetMapping({"/", "/index"})
    public String index(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        request.setAttribute("testParam", "测试参数");
        return "index";
    }

    @GetMapping("/me")
    @ResponseBody
    public Authentication me(Authentication authentication) {
        return authentication;
    }
}
