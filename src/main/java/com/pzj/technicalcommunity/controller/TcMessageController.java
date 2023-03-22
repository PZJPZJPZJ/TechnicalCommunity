package com.pzj.technicalcommunity.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pzj.technicalcommunity.entity.TcChat;
import com.pzj.technicalcommunity.entity.TcMessage;
import com.pzj.technicalcommunity.service.ITcChatService;
import com.pzj.technicalcommunity.service.ITcMessageService;
import com.pzj.technicalcommunity.util.JwtUtils;
import com.pzj.technicalcommunity.util.ResultPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

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
    @Autowired
    private ITcChatService iTcChatService;

    /**
     * Description 时间倒序分页展示当前聊天消息
     * Param 分页数据，聊天ID(json)
     * Return 聊天分页信息(json)
     */
    @RequestMapping("/list")
    public ResultPackage list(Integer id){
        //设置查询条件
        QueryWrapper<TcMessage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("message_chat",id).orderByAsc("message_time");
        //执行查询
        List<TcMessage> list = iTcMessageService.list(queryWrapper);
        return ResultPackage.pack(list);
    }

    @RequestMapping("/save")
    public ResponseEntity<String> save(@RequestHeader HashMap hashMapHeader,@RequestBody TcMessage tcMessage){
        //根据token获取当前用户
        Integer userId = Integer.valueOf(JwtUtils.getClaimByToken((String) hashMapHeader.get("authorization")).getSubject());
        tcMessage.setMessageUser(userId);
        if (iTcMessageService.save(tcMessage)){
            UpdateWrapper<TcChat> updateWrapper = new UpdateWrapper<>();
            updateWrapper.setSql("chat_unread = chat_unread + 1").eq("chat_id", tcMessage.getMessageChat());
            iTcChatService.update(updateWrapper);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
