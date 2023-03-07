package com.pzj.technicalcommunity.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TcUserDTO {
    private Integer userId;

    private String userName;

    private String userSign;

    private String userSex;

    private LocalDateTime userBirth;
}
