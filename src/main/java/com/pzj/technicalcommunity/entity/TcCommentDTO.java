package com.pzj.technicalcommunity.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TcCommentDTO {
    private Integer commentId;

    private Integer commentPost;

    private Integer commentUser;
    private String userName;
    private String userCover;

    private String commentContent;

    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss")
    private LocalDateTime commentTime;
}
