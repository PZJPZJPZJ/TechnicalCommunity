package com.pzj.technicalcommunity.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pzj.technicalcommunity.entity.TcUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pzj.technicalcommunity.entity.TcUserDTO;
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
public interface TcUserMapper extends BaseMapper<TcUser> {

    IPage<TcUserDTO> pageSafe(Page<TcUserDTO> page);
}
