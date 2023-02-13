package com.pzj.technicalcommunity.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pzj.technicalcommunity.entity.TcChat;
import com.pzj.technicalcommunity.entity.TcMessage;
import com.pzj.technicalcommunity.service.ITcMessageService;
import com.pzj.technicalcommunity.util.ResultPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pzj
 * @since 2023-02-06
 */
@RestController
@RequestMapping("/message")
public class TcMessageController {
    @Autowired
    private ITcMessageService iTcMessageService;

    /**
     * Description 时间倒序分页展示当前聊天消息
     * Param 分页数据，聊天ID(json)
     * Return 聊天分页信息(json)
     */
    @RequestMapping("/list")
    public ResultPackage list(@RequestBody HashMap hashMap){
        //设置页数和页大小
        Page<TcMessage> page = new Page<>();
        page.setCurrent((int)hashMap.get("pageNum"));
        page.setSize((int)hashMap.get("pageSize"));
        //设置查询条件
        QueryWrapper<TcMessage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("message_chat",hashMap.get("chatSend")).orderByDesc("message_time");
        //执行查询
        IPage<TcMessage> iPage = iTcMessageService.page(page,queryWrapper);
        return ResultPackage.pack(iPage.getRecords(),iPage.getTotal());
    }

}
