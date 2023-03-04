package com.pzj.technicalcommunity.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TcPostDTO {
    private Integer postId;

    private Integer postTag;
    private String tagName;

    private Integer postUser;
    private String userName;

    private String postTitle;

    private String postContent;

    private LocalDateTime postTime;

    private Boolean postTop;

    private BigDecimal postPrice;
}
