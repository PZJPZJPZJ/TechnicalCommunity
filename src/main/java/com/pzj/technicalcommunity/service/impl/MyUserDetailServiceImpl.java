package com.pzj.technicalcommunity.service.impl;

import com.pzj.technicalcommunity.entity.TcUser;
import com.pzj.technicalcommunity.mapper.TcUserMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailServiceImpl implements UserDetailsService {
    @Resource
    private TcUserMapper tcUserMapper;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        TcUser tcUser = tcUserMapper.selectById(s);
        if(tcUser == null){
            throw new UsernameNotFoundException("Invalid id or password.");
        }
        return new org.springframework.security.core.userdetails.User(tcUser.getUserId().toString(), tcUser.getUserPassword(), getAuthorities(tcUser));
    }

    private List<GrantedAuthority> getAuthorities(TcUser tcUser) {
        //获取实体类管理员判断字段
        String userRole;
        //将判断字段转换为字符串以便区分
        if(tcUser.getUserAdmin()){
            userRole = "ADMIN";
        }
        else{
            userRole = "USER";
        }
        //将字符串装入Security的角色类中
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userRole));
        return authorities;
    }
}
