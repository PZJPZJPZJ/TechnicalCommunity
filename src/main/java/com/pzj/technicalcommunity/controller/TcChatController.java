package com.pzj.technicalcommunity.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pzj.technicalcommunity.entity.TcChat;
import com.pzj.technicalcommunity.service.ITcChatService;
import com.pzj.technicalcommunity.util.ResultPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/chat")
public class TcChatController {

    @Autowired
    private ITcChatService iTcChatService;

    /**
     * Description 时间倒序展示当前用户私信列表
     * Param 分页数据，发起用户ID(json)
     * Return 用户私信列表(json)
     */
    @RequestMapping("/list")
    public ResultPackage list(@RequestBody HashMap hashMap){
        //设置页数和页大小
        Page<TcChat> page = new Page<>();
        page.setCurrent((int)hashMap.get("pageNum"));
        page.setSize((int)hashMap.get("pageSize"));
        //设置查询条件
        QueryWrapper<TcChat> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chat_send",hashMap.get("chatSend")).orderByDesc("chat_time").orderByDesc("chat_unread");
        //执行查询
        IPage<TcChat> iPage = iTcChatService.page(page,queryWrapper);
        return ResultPackage.success(iPage.getRecords(),iPage.getTotal());
    }

    /**
     * Description 新增聊天
     * Param 聊天实体(json)
     * Return 执行结果(bool)
     */
    @PostMapping("/save")
    public boolean save(@RequestBody TcChat tcChat){
        return iTcChatService.save(tcChat);
    }

    /**
     * Description 删除聊天
     * Param 聊天ID(url)
     * Return 执行结果(bool)
     */
    @GetMapping("/delete")
    public boolean delete(Integer id){
        return iTcChatService.removeById(id);
    }
}
