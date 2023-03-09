package com.pzj.technicalcommunity.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pzj.technicalcommunity.entity.TcChat;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pzj.technicalcommunity.entity.TcChatDTO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author pzj
 * @since 2023-02-13
 */
@Mapper
public interface TcChatMapper extends BaseMapper<TcChat> {

    List<TcChatDTO> listMyChat(Integer userId);
}
