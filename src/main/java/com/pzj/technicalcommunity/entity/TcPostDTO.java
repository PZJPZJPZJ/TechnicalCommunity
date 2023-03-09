package com.pzj.technicalcommunity.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss")
    private LocalDateTime postTime;

    private Boolean postTop;

    private BigDecimal postPrice;

    private String postImg;
}
