package com.whz.spring.security.oauth2.demo.config;

import com.whz.spring.security.oauth2.demo.handler.CustomAuthenticationAccessDeniedHandler;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.annotation.Resource;


/**
 * @author ChengJianSheng
 * @date 2019-03-03
 */
@Configuration
@EnableOAuth2Sso
/** 注意：开启Controller层的访问方法权限，与注解@PreAuthorize配合 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private CustomAuthenticationAccessDeniedHandler customAuthenticationAccessDeniedHandler;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/bootstrap/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.logout().logoutSuccessUrl("http://localhost:8000/logout")
                .and()
                .exceptionHandling().accessDeniedHandler(customAuthenticationAccessDeniedHandler)
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .csrf().disable();
    }

}

