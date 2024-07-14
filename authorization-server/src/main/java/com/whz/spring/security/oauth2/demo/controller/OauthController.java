package com.whz.spring.security.oauth2.demo.controller;


import com.whz.spring.security.oauth2.demo.config.SecurityConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 模版引擎方式引导至自定义登录界面 /oauth/login 在 spring security中定义
 */
@Slf4j
@Controller
public class OauthController {

    /**
     * 登录页面
     *
     * @param model
     * @return
     */
    @GetMapping("/oauth/login")
    public String loginView(Model model) {
        // 返回登录的请求路径
        model.addAttribute("action", SecurityConfig.login_processing_url);
        return "form-login";
    }

}
