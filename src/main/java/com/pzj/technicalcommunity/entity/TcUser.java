package com.pzj.technicalcommunity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class TcUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    private Boolean userAdmin;

    private String userPassword;

    private String userName;

    private String userCover;

    private String userSign;

    private String userSex;

    private LocalDateTime userBirth;


}
