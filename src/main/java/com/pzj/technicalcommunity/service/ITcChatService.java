package com.pzj.technicalcommunity.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pzj.technicalcommunity.entity.TcChat;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pzj.technicalcommunity.entity.TcChatDTO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pzj
 * @since 2023-02-13
 */
public interface ITcChatService extends IService<TcChat> {

    List<TcChatDTO> listMyChat(Integer userId);
}
