package com.pzj.technicalcommunity.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TcCommentDTO {
    private Integer commentId;

    private Integer commentPost;

    private Integer commentUser;
    private String userName;

    private String commentContent;

    private LocalDateTime commentTime;
}
