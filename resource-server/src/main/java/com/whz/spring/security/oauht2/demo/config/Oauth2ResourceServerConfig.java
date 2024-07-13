package com.whz.spring.security.oauht2.demo.config;

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import javax.annotation.Resource;

/**
 * 资源服务器配置
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 19-7-15 上午9:05
 */
@Configuration
@EnableResourceServer
public class Oauth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

    /**
     * 配置文件
     */
    @Resource
    private ResourceServerProperties resourceServerProperties;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        // 设置资源服务器的id,从配置文件中读取
        resources.resourceId(resourceServerProperties.getResourceId());

        // 设置资源服务器的id
        // resources.resourceId("resourceOne");
    }


}
