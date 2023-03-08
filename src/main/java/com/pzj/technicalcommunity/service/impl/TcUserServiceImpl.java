package com.pzj.technicalcommunity.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pzj.technicalcommunity.entity.TcUser;
import com.pzj.technicalcommunity.entity.TcUserDTO;
import com.pzj.technicalcommunity.mapper.TcUserMapper;
import com.pzj.technicalcommunity.service.ITcUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class TcUserServiceImpl extends ServiceImpl<TcUserMapper, TcUser> implements ITcUserService {

    @Resource
    private TcUserMapper tcUserMapper;

    @Override
    public IPage<TcUserDTO> pageSafe(Page<TcUserDTO> page) {
        return tcUserMapper.pageSafe(page);
    }
}
