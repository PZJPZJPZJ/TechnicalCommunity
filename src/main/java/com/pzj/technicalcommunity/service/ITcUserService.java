package com.pzj.technicalcommunity.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pzj.technicalcommunity.entity.TcUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pzj.technicalcommunity.entity.TcUserDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pzj
 * @since 2023-02-06
 */
public interface ITcUserService extends IService<TcUser> {

    IPage<TcUserDTO> pageSafe(Page<TcUserDTO> page);
}
