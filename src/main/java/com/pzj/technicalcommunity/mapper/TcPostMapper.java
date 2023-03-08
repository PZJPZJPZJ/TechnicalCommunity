package com.pzj.technicalcommunity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pzj.technicalcommunity.entity.TcPost;
import com.pzj.technicalcommunity.entity.TcPostDTO;
import org.mapstruct.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author pzj
 * @since 2023-02-06
 */
@Mapper
public interface TcPostMapper extends BaseMapper<TcPost> {
    IPage<TcPostDTO> pageTop(Page<TcPostDTO> page);

    TcPostDTO listOne(Integer id);

    IPage<TcPostDTO> pageUser(Page<TcPostDTO> page,Integer id);

    IPage<TcPostDTO> pageAll(Page<TcPostDTO> page);

    IPage<TcPostDTO> pageTag(Page<TcPostDTO> page, Integer id);
}
