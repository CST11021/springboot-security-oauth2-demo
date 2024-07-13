package com.whz.spring.security.oauth2.demo.controller;

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
public class ConfirmAccessController {

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
        view.setViewName("confirm-access");
        view.addObject("clientId", authorizationRequest.getClientId());
        view.addObject("scopes",authorizationRequest.getScope());
        view.addObject("scopeName",String.join(",",authorizationRequest.getScope()));
        return view;
    }

}
