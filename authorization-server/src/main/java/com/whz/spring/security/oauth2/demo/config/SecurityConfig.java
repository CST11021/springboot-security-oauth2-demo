package com.whz.spring.security.oauth2.demo.config;

import com.whz.spring.security.oauth2.demo.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *  配置 spring security web 安全
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 从数据库存用户信息
     *
     * @param auth the {@link AuthenticationManagerBuilder} to use
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

        // 也可以使用InMemoryUserDetailsManager实现
        // InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        // manager.createUser(User.withUsername("user").password(passwordEncoder().encode("123456")).authorities("ROLE_USER").build());
        // manager.createUser(User.withUsername("admin").password(passwordEncoder().encode("admin")).authorities("ROLE_ADMIN").build());
        // auth.userDetailsService(manager).passwordEncoder(passwordEncoder());
    }

    /**
     * 不加这个也不影响，不会拦截静态资源
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/assets/**", "/css/**", "/images/**","/fonts/**","/js/**","/vendor/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login", "/oauth/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/oauth/login")
                .loginProcessingUrl(securityProperties.getLoginProcessingUrl())
                .and()
                .csrf().disable();
    }



    /**
     *   Spring 5 之后必须对密码进行加密
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }

    /**
     * authorizatin_type 为 password 时需要
     *
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * @return 数据库查询用户令牌
     */
    /*
     * InMemoryUserDetailsManager 创建两个内存用户
     * 用户名 user 密码 123456 角色 ROLE_USER
     * 用户名 admin 密码 admin 角色 ROLE_ADMIN
     **/
       /*
    @Bean
    protected UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager manage = new InMemoryUserDetailsManager();
        manage.createUser(User.withUsername("user")
                        .password(passwordEncoder().encode("123456"))
                        .authorities("ROLE_USER").build() );
        manage.createUser(User.withUsername("admin")
                .password(passwordEncoder().encode("admin"))
                .authorities("ROLE_ADMIN").build() );
        return manage;
    }
        */


}