package com.whz.spring.security.oauth2.demo.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * user-agent授权 authorization server 发放授权码 code 的模版引擎自定义授权界面
 */
@Controller
@SessionAttributes("authorizationRequest")  // 重要！
public class AuthorizationController {

    /**
     * 授权页面
     * 注：AuthorizationEndpoint类中定义了该默认授权页面
     *
     * @param model
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/oauth/confirm_access")
    public ModelAndView getAccessConfirmation(Map<String, Object> model, HttpServletRequest request) throws Exception {
        AuthorizationRequest authorizationRequest = (AuthorizationRequest) model.get("authorizationRequest");
        ModelAndView view = new ModelAndView();
        view.setViewName("authorization");
        view.addObject("clientId", authorizationRequest.getClientId());
        view.addObject("scopes",authorizationRequest.getScope());
        view.addObject("scopeName",String.join(",",authorizationRequest.getScope()));
        return view;
    }



    public static void main(String[] args) {
        // $2a$10$eqvHalnutg/ShbFKkSRRCuXAAeZCHTfp2V1cImRQ0KhU48923w/De
        System.out.println(new BCryptPasswordEncoder().encode("code-secret-8888"));
        // $2a$10$Qq5hJf4YGFvDVREQaTwneeTq5bplvRtr0Z2qwj5FKrXddfwu3BU1.
        System.out.println(new BCryptPasswordEncoder().encode("oauth2"));
        // $2a$10$K7lxQh3edvdsqBnLl3bd/.JMuZHmSHrEDBbgGDRz4WgfClE/nb7Ci
        System.out.println(new BCryptPasswordEncoder().encode("clientOne"));
        // $2a$10$86z8h9EeD.VFX6w9Aa/6f.35iG2T9DzxPnobbaJ84qnCCgs1vwQJO
        // $2a$10$Htzoqq.4i5gnp/Z7vXZeE.8YG9jLphfay9n89bClaLJfy5myW5ml.
        System.out.println(new BCryptPasswordEncoder().encode("clientTwo"));
    }
}
