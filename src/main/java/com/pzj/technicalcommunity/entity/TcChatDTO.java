package com.pzj.technicalcommunity.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TcChatDTO {
    private Integer chatId;

    private Integer chatSend;

    private Integer chatReceive;
    private String userName;
    private String userSign;

    private Integer chatUnread;

    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss")
    private LocalDateTime chatTime;
}
