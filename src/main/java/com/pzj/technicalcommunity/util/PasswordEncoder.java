package com.pzj.technicalcommunity.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {
    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode =  bCryptPasswordEncoder.encode("123456");
        System.out.println(encode);
    }
}
