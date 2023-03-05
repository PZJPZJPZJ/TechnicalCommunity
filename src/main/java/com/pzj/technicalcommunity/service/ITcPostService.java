package com.pzj.technicalcommunity.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pzj.technicalcommunity.entity.TcPost;
import com.pzj.technicalcommunity.entity.TcPostDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pzj
 * @since 2023-02-06
 */
public interface ITcPostService extends IService<TcPost> {

    IPage<TcPostDTO> pageTop(Page<TcPostDTO> page);
    TcPostDTO listOne(Integer id);
}
