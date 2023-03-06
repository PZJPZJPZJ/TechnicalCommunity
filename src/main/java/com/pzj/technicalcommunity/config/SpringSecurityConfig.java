package com.pzj.technicalcommunity.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pzj.technicalcommunity.common.JwtAuthenticationFilter;
import com.pzj.technicalcommunity.common.LoginFilter;
import com.pzj.technicalcommunity.service.impl.MyUserDetailServiceImpl;
import com.pzj.technicalcommunity.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    private final MyUserDetailServiceImpl myUserDetailService;

    @Autowired
    public SpringSecurityConfig(MyUserDetailServiceImpl myUserDetailService) {
        this.myUserDetailService = myUserDetailService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //自定义查询数据库登录重写，定义密码加密方式
        auth.userDetailsService(myUserDetailService).passwordEncoder(passwordEncoder());
    }

    //自定义认证过滤器
    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager());
        return jwtAuthenticationFilter;
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
        //认证成功处理
        loginFilter.setAuthenticationSuccessHandler((req,resp,auth)->{
            Map<String,Object> result = new HashMap<String,Object>();
            //设定HTTP状态
            resp.setStatus(HttpStatus.OK.value());
            //根据用户ID生成token，写入HTTP头
            String jwt = JwtUtils.generateToken(auth.getName());
            resp.setHeader("authorization",jwt);
            //返回json信息
            result.put("msg","登陆成功");
            result.put("userinfo",(User)(auth.getPrincipal()));
            resp.setContentType("application/json;charset=UTF-8");
            resp.getWriter().println(new ObjectMapper().writeValueAsString(result));
        });
        //认证失败处理
        loginFilter.setAuthenticationFailureHandler((req,resp,ex)->{
            Map<String,Object> result = new HashMap<String,Object>();
            resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            result.put("msg","登陆失败"+ex.getMessage());
            resp.setContentType("application/json;charset=UTF-8");
            resp.getWriter().println(new ObjectMapper().writeValueAsString(result));
        });
        return loginFilter;
    }

    private static final String URL_WHITELIST[] = {
            //放行登录
            "/login",
            //放行登出
            "/logout",
            //放行主页检测登陆状态
            "/user/token",
            //放行用户注册
            "/user/save"
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
                    resp.setHeader("authorization",null);
                    result.put("msg","注销成功");
                    resp.setContentType("application/json;charset=UTF-8");
                    resp.getWriter().println(new ObjectMapper().writeValueAsString(result));
                })
        //不通过session获取SecurityContext
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        //拦截规则
                .and()
                .authorizeRequests()
                //白名单放行
                .antMatchers(URL_WHITELIST).permitAll()
                //所有请求必须认证
                .anyRequest().authenticated()
        //自定义过滤器
                .and()
                .addFilter(jwtAuthenticationFilter())
                .addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
