package com.whz.spring.security.oauth2.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * 定义授权服务器配置
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /** 数据源 */
    private final DataSource dataSource;

    /** 令牌增强器 */
    private InfoTokenEnhancer tokenEnhancer;

    /** authorization_type 为 password 时需要 */
    private AuthenticationManager authenticationManager;

    /** 使用 oauth/refresh_token 端点api 时需要手动注入到授权服务器安全中 */
    private UserDetailsService userDetailsService;

    public AuthorizationServerConfig(DataSource dataSource,
                                     InfoTokenEnhancer tokenEnhancer,
                                     AuthenticationManager authenticationManager,
                                     UserDetailsService userDetailsService) {
        this.dataSource = dataSource;
        this.tokenEnhancer = tokenEnhancer;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    /**
     * 配置客户端的service，就是应用怎么获取到客户端的信息，一般来说是从内存或者数据库中获取
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 配置如何读取接入的客户端信息，这里可以自定义实现
        clients.withClientDetails(clientDetails());

        // 也可以从内存读取
        // clients.inMemory()
        //         .withClient("oauth2")
        //         .secret("$2a$10$wlgcx61faSJ8O5I4nLiovO9T36HBQgh4RhOQAYNORCzvANlInVlw2")
        //         .resourceIds("oauth2")
        //         .authorizedGrantTypes("password", "authorization_code", "refresh_token")
        //         .authorities("ROLE_ADMIN", "ROLE_USER")
        //         .scopes("all")
        //         .accessTokenValiditySeconds(Math.toIntExact(Duration.ofHours(1).getSeconds()))
        //         .refreshTokenValiditySeconds(Math.toIntExact(Duration.ofHours(1).getSeconds()))
        //         .redirectUris("http://example.com")
        //         .and()
        //         .withClient("test")
        //         .secret("$2a$10$wlgcx61faSJ8O5I4nLiovO9T36HBQgh4RhOQAYNORCzvANlInVlw2")
        //         .resourceIds("oauth2")
        //         .authorizedGrantTypes("password", "authorization_code", "refresh_token")
        //         .authorities("ROLE_ADMIN", "ROLE_USER")
        //         .scopes("all")
        //         .accessTokenValiditySeconds(Math.toIntExact(Duration.ofHours(1).getSeconds()))
        //         .refreshTokenValiditySeconds(Math.toIntExact(Duration.ofHours(1).getSeconds()))
        //         .redirectUris("http://example.com");
    }

    /**
     * 配置授权服务器各个端点的非安全功能，如：令牌存储，令牌自定义，用户批准和授权类型
     * 使用 oauth/refresh_token 端点api时 userDetailsService 需要手动注入到授权服务器安全中
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer, jwtAccessTokenConverter()));

        endpoints.tokenStore(tokenStore());
        endpoints.tokenEnhancer(tokenEnhancerChain);
        endpoints.userDetailsService(userDetailsService);
        endpoints.authenticationManager(authenticationManager);

        // endpoints.tokenGranter(tokenGranter(endpoints));
    }

    /**
     * 配置授权服务器的安全信息，比如 ssl 配置、checktoken 是否允许访问，是否允许客户端的表单身份验证等
     *
     * @param security
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        // 资源服务器就能够向授权服务器验证并解析 token 获取用户信息(授权服务器添加 check_token 端点支持。)
        security.checkTokenAccess("isAuthenticated()");
        // 允许资源服务器能够访问公钥端点 oauth/token_key
        security.tokenKeyAccess("isAuthenticated()");
        // allowFormAuthenticationForClients允许client在页面使用form的方式进行authentication的授权
        // security.allowFormAuthenticationForClients();
    }

    /**
     * 声明ClientDetails的实现，这里是从数据库（需要添加：oauth_client_details表）读取客户端信息，也可以使用InMemoryClientDetailsService实现
     *
     * @return
     */
    @Bean
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }

    /**
     * 令牌转换器，对称密钥加密
     *
     * @return JwtAccessTokenConverter
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();

        //***** 对称加密  Begin *********
        // setSigningKey 方法中的 isPublic 方法已经判断是否使用对称 or 非对称方法
        // converter.setSigningKey("oauth2");
        //***** 对称加密  End *********

        //****** 非对称加密  Begin *********
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("oauth2.jks"), "your_pwd".toCharArray());
         //  这里的 getKeyPair 内容为生成密钥对时指定的 alias 名
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("oauth2"));
        //****** 非对称加密  End *********

        return converter;
    }

    /**
     * 令牌存储实现，这里使用JWT方式存储令牌
     *
     * @return JwtTokenStore
     */
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    // public TokenStore redisTokenStore() {
    //     return new RedisTokenStore(redisConnectionFactory);
    // }


}
