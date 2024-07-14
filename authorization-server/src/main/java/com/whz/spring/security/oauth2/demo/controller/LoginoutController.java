package com.whz.spring.security.oauth2.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author 盖伦
 * @Date 2024/7/14
 */
@Slf4j
@Controller
public class LoginoutController {

    /**
     * 自定义登出，和 HttpSecurity.logout().logoutSuccessUrl("http://localhost:8000/logout") 效果一样，只能logout一个client不能logout所有的client
     *
     * @param request
     * @param response
     */
    @RequestMapping("/oauth/exit")
    public void exit(HttpServletRequest request, HttpServletResponse response) {
        // token can be revoked here if needed
        new SecurityContextLogoutHandler().logout(request, null, null);
        try {
            // sending back to client app
            log.info("退出请求, 回调客户端: {}", request.getHeader("referer"));
            response.sendRedirect(request.getHeader("referer"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
