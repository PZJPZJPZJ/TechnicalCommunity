package com.pzj.technicalcommunity.common;

import com.pzj.technicalcommunity.util.JwtUtils;
import io.jsonwebtoken.JwtException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwt = request.getHeader("Authorization");
        //遇到白名单的url直接放行或者不在过滤器链中直接忽略
        if ("undefined".equals(jwt) || null == jwt || "".equals(jwt)) {
            chain.doFilter(request, response);
            return;
        }
        //遇到不在白名单中的url判断token是否正常
        if (JwtUtils.getClaimByToken(jwt) == null) {
            throw new JwtException("token异常");
        }
        if (JwtUtils.isTokenExpired(jwt)) {
            throw new JwtException("token已过期");
        }
        //通过token获取用户ID以及用户信息
        String userId = JwtUtils.getClaimByToken(jwt).getSubject();
        //写入用户信息授权
        UsernamePasswordAuthenticationToken token
                = new UsernamePasswordAuthenticationToken(userId, null,null);
        SecurityContextHolder.getContext().setAuthentication(token);
        chain.doFilter(request, response);
    }
}
