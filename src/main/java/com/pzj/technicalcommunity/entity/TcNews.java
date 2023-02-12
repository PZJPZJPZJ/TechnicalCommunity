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
 * @since 2023-02-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TcNews implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "news_id", type = IdType.AUTO)
    private Integer newsId;

    private Integer newsAdmin;

    private String newsTitle;

    private String newsContent;

    private String newsCover;

    private LocalDateTime newsTime;


}
