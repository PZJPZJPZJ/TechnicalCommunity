package com.pzj.technicalcommunity.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pzj.technicalcommunity.entity.TcComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pzj.technicalcommunity.entity.TcCommentDTO;
import org.mapstruct.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author pzj
 * @since 2023-02-13
 */
@Mapper
public interface TcCommentMapper extends BaseMapper<TcComment> {

    IPage<TcCommentDTO> pagePost(Integer id,Page<TcCommentDTO> page);
}
