package com.pzj.technicalcommunity.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pzj.technicalcommunity.common.LoginFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        inMemoryUserDetailsManager.createUser(User.withUsername("root").password("{noop}123").roles("admin").build());
        return inMemoryUserDetailsManager;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        //指定登录url
        loginFilter.setFilterProcessesUrl("/login");
        //指定接收json用户名与密码参数名
        loginFilter.setUsernameParameter("id");
        loginFilter.setPasswordParameter("password");
        loginFilter.setAuthenticationManager(authenticationManagerBean());
        //认证成功处理（函数式接口更方便）
        loginFilter.setAuthenticationSuccessHandler((req,resp,auth)->{
            Map<String,Object> result = new HashMap<String,Object>();
            resp.setStatus(HttpStatus.OK.value());
            result.put("msg","登陆成功");
            result.put("用户信息",(User)(auth.getPrincipal()));
            resp.setContentType("application/json;charset=UTF-8");
            String s = new ObjectMapper().writeValueAsString(result);
            resp.getWriter().println(s);
        });
        //认证失败处理
        loginFilter.setAuthenticationFailureHandler((req,resp,ex)->{
            Map<String,Object> result = new HashMap<String,Object>();
            resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            result.put("msg","登陆失败"+ex.getMessage());
            resp.setContentType("application/json;charset=UTF-8");
            String s = new ObjectMapper().writeValueAsString(result);
            resp.getWriter().println(s);
        });
        return loginFilter;
    }

    private static final String URL_WHITELIST[] = {
            "/login",
            "/logout",
            "/captcha",
            "/password",
            "/image/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        //开启跨域，关闭csrf
                .cors()
                .and()
                .csrf().disable()
        //认证处理设置
                .formLogin()
                //认证异常处理（防止登录失败返回登录页面）
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((req,resp,ex)->{
                    resp.setContentType("application/json;charset=UTF-8");
                    resp.setStatus(HttpStatus.UNAUTHORIZED.value());
                    resp.getWriter().println("请认证");
                })
                //登出处理
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler((req,resp,auth)->{
                    Map<String,Object> result = new HashMap<String,Object>();
                    resp.setStatus(HttpStatus.OK.value());
                    result.put("msg","注销成功");
                    //result.put("用户信息",auth.getPrincipal());
                    resp.setContentType("application/json;charset=UTF-8");
                    String s = new ObjectMapper().writeValueAsString(result);
                    resp.getWriter().println(s);
                })
        //禁用session
//                .and()
//                .sessionManagement()
//                //无状态
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        //拦截规则
                .and()
                .authorizeRequests()
                //白名单放行
                .antMatchers(URL_WHITELIST).permitAll()
                //所有请求必须认证
                .anyRequest().authenticated()
        //自定义过滤器
                .and()
                .addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
