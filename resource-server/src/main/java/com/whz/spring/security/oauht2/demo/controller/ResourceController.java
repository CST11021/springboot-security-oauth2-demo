package com.whz.spring.security.oauht2.demo.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 19-7-15 上午10:07
 */
@RestController
public class ResourceController {

    /**
     * curl -XGET http://localhost:9000/auth/getResource \
     *  -H 'Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsib2F1dGgyIiwicmVzb3VyY2VPbmUiXSwidXNlcl9uYW1lIjoiYWRtaW4iLCJzY29wZSI6WyJhbGwiXSwib3JnYW5pemF0aW9uIjoiYWRtaW4iLCJ3aHpUZXN0UGFyYW0iOiJ3aHrmtYvor5UiLCJleHAiOjE3MjA4NjQ0MzEsImF1dGhvcml0aWVzIjpbIm1lbWJlcjphZGQiXSwianRpIjoiM2FiNGI0NDQtZTAwYS00YTkxLWJiNTItZGRiZjVhOGIxZDQ2IiwiY2xpZW50X2lkIjoib2F1dGgyIn0.GrA2eCauov0darQmeI2s2oEqVKxSeY6xqgYyQLfQwn3DKj6N5T1tDVzvDP2o3Q-KwSZRNCpnj0_MBoKAJHi2lNlT7ujZv92Ax3r5qUv1uHi2nVtyKk2qnu7XPSxXgtzsDWbGL71rkMH2IyGMjpHjrUA8lO7EluKaqg5MtsEIald_dhOtZfDXSll3zbS62al2aXbL4cHIccWoQYSFIJHFGoGeseVeRKd-qXnFuMEa1SDB8XE157Z9YMaLVnvBSxFIG2mQ-CWpyFBmEDeo0VkPEkJCPei6R6J-rvz0c6BEZcz960hLsOi4lEbmEmBs29pBb92VvTzAcMgtFH0D_Ukttg'
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/auth/getResource")
    public String getRes() {
        return "test";
    }

    /**
     * 获取当前登录的用户信息
     *
     * @param principal 用户信息
     * @return http 响应
     */
    @GetMapping("/auth/info")
    public HttpEntity<?> info(Principal principal) {
        return ResponseEntity.ok(principal);
    }

    /**
     * 获取认证信息
     *
     * @param authentication
     * @return
     */
    @ResponseBody
    @GetMapping("/auth/me")
    public Authentication me(Authentication authentication) {
        return authentication;
    }

}
