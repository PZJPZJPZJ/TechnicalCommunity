package com.pzj.technicalcommunity.entity;

import java.math.BigDecimal;
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
public class TcPost implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "post_id", type = IdType.AUTO)
    private Integer postId;

    private Integer postTag;

    private Integer postUser;

    private String postTitle;

    private String postContent;

    private LocalDateTime postTime;

    private Boolean postTop;

    private Integer postLike;

    private BigDecimal postPrice;


}
