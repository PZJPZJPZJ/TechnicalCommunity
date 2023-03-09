package com.pzj.technicalcommunity.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pzj.technicalcommunity.entity.TcChat;
import com.pzj.technicalcommunity.entity.TcChatDTO;
import com.pzj.technicalcommunity.mapper.TcChatMapper;
import com.pzj.technicalcommunity.service.ITcChatService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pzj
 * @since 2023-02-13
 */
@Service
public class TcChatServiceImpl extends ServiceImpl<TcChatMapper, TcChat> implements ITcChatService {
    @Resource
    private TcChatMapper tcChatMapper;

    @Override
    public List<TcChatDTO> listMyChat(Integer userId) {
        return tcChatMapper.listMyChat(userId);
    }
}
