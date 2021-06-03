package com.project.data_cloud_server.config.security;

import com.project.data_cloud_server.config.captcha.CaptchaFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    LoginSuccessHandler loginSuccessHandler;
    @Autowired
    LoginFailureHandler loginFailureHandler;
    @Autowired
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    JwtAccessDeniedHandler jwtAccessDeniedHandler;
    @Autowired
    JwtLogoutSuccessHandler jwtLogoutSuccessHandler;
    @Autowired
    CaptchaFilter captchaFilter;

    private static final String[] URL_ALLOWED={"/login","/captcha","/register","/logout"};

    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        return new JwtAuthenticationFilter(authenticationManager());
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                //配置认证
                .authorizeRequests()
                .antMatchers(URL_ALLOWED).permitAll()
                .anyRequest().authenticated()
                //配置登录
                .and()
                .formLogin()
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
                // 不会创建 session
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //退出登录
                .and()
                .logout()
                .logoutSuccessHandler(jwtLogoutSuccessHandler)
                //异常处理器
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                //自定义过滤器，jwt认证过滤器和验证码过滤器
                .and()
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class);  //添加验证码过滤器




                ;




    }
}
