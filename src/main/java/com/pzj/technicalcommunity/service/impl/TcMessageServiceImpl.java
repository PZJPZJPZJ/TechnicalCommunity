package com.pzj.technicalcommunity.service.impl;

import com.pzj.technicalcommunity.entity.TcMessage;
import com.pzj.technicalcommunity.mapper.TcMessageMapper;
import com.pzj.technicalcommunity.service.ITcMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pzj
 * @since 2023-02-04
 */
@Service
public class TcMessageServiceImpl extends ServiceImpl<TcMessageMapper, TcMessage> implements ITcMessageService {

}
