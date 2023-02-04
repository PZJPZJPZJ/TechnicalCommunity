package com.pzj.technicalcommunity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author pzj
 * @since 2023-02-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TcChat implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "chat_id", type = IdType.AUTO)
    private Integer chatId;

    private Integer chatSend;

    private Integer chatReceive;


}
