package com.pzj.technicalcommunity.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pzj.technicalcommunity.entity.TcComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pzj.technicalcommunity.entity.TcCommentDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pzj
 * @since 2023-02-13
 */
public interface ITcCommentService extends IService<TcComment> {

    IPage<TcCommentDTO> pagePost(Integer id,Page<TcCommentDTO> page);
}
