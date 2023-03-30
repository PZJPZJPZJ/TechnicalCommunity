package com.pzj.technicalcommunity.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TcUserDTO {
    private Integer userId;

    private Boolean userAdmin;

    private String userName;

    private String userCover;

    private String userSign;

    private String userSex;

    private LocalDateTime userBirth;
}
