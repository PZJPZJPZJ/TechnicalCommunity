package com.pzj.technicalcommunity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author pzj
 * @since 2023-02-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TcMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "message_id", type = IdType.AUTO)
    private Integer messageId;

    private Integer messageChat;

    private String messageContent;

    private LocalDateTime messageTime;

    private Boolean messageUnread;


}
