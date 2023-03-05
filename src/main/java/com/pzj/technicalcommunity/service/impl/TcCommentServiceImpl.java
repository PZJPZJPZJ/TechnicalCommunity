package com.pzj.technicalcommunity.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pzj.technicalcommunity.entity.TcComment;
import com.pzj.technicalcommunity.entity.TcCommentDTO;
import com.pzj.technicalcommunity.mapper.TcCommentMapper;
import com.pzj.technicalcommunity.service.ITcCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pzj
 * @since 2023-02-13
 */
@Service
public class TcCommentServiceImpl extends ServiceImpl<TcCommentMapper, TcComment> implements ITcCommentService {

    @Resource
    private TcCommentMapper tcCommentMapper;

    @Override
    public IPage<TcCommentDTO> pagePost(Integer id,Page<TcCommentDTO> page) {
        return tcCommentMapper.pagePost(id,page);
    }
}
