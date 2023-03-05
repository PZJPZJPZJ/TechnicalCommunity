package com.pzj.technicalcommunity.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pzj.technicalcommunity.entity.TcPost;
import com.pzj.technicalcommunity.entity.TcPostDTO;
import com.pzj.technicalcommunity.mapper.TcPostMapper;
import com.pzj.technicalcommunity.service.ITcPostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pzj
 * @since 2023-02-06
 */
@Service
public class TcPostServiceImpl extends ServiceImpl<TcPostMapper, TcPost> implements ITcPostService {

    @Resource
    private TcPostMapper tcPostMapper;
    @Override
    public IPage<TcPostDTO> pageTop(Page<TcPostDTO> page) {
        return tcPostMapper.pageTop(page);
    }

    @Override
    public TcPostDTO listOne(Integer id) {
        return tcPostMapper.listOne(id);
    }


}
