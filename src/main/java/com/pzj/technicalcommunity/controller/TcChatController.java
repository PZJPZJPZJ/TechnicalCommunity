package com.pzj.technicalcommunity.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pzj.technicalcommunity.entity.TcChat;
import com.pzj.technicalcommunity.entity.TcChatDTO;
import com.pzj.technicalcommunity.entity.TcUser;
import com.pzj.technicalcommunity.service.ITcChatService;
import com.pzj.technicalcommunity.service.ITcUserService;
import com.pzj.technicalcommunity.util.JwtUtils;
import com.pzj.technicalcommunity.util.ResultPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpHeaders;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private ITcUserService iTcUserService;

    /**
     * Description 时间倒序展示当前用户私信列表
     * Param 分页数据，发起用户ID(json)
     * Return 用户私信列表(json)
     */
    @RequestMapping("/list")
    public ResultPackage list(@RequestHeader HashMap hashMapHeader){
        //根据token获取当前用户
        Integer userId = Integer.valueOf(JwtUtils.getClaimByToken((String) hashMapHeader.get("authorization")).getSubject());
        //执行查询
        List<TcChatDTO> list = iTcChatService.listMyChat(userId);
        return ResultPackage.pack(list);
    }

    /**
     * 清除未读消息
     * @param chatId
     * @return 状态码
     */
    @RequestMapping("/clear")
    public ResponseEntity<String> clear(Integer chatId){
        TcChat tcChat = new TcChat();
        tcChat.setChatId(chatId);
        tcChat.setChatUnread(0);
        if(iTcChatService.updateById(tcChat)){
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Description 新增聊天
     * Param 聊天实体(json)
     * Return 执行结果(bool)
     */
    @RequestMapping("/save")
    public ResponseEntity<String> save(@RequestHeader HashMap hashMapHeader,Integer id){
        //根据token获取当前用户
        Integer userId = Integer.valueOf(JwtUtils.getClaimByToken((String) hashMapHeader.get("authorization")).getSubject());
        TcChat tcChat = new TcChat();
        tcChat.setChatSend(userId);
        tcChat.setChatReceive(id);
        //检测用户是否存在
        QueryWrapper<TcUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",id);
        //检测聊天是否存在
        QueryWrapper<TcChat> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("chat_receive",id).eq("chat_send",userId);
        //检测聊天是否已存在对方发起
        QueryWrapper<TcChat> queryWrapper2 = new QueryWrapper<>();
        queryWrapper1.eq("chat_receive",userId).eq("chat_send",id);
        //计算查询结果计数，确认目标用户是否存在，确认是否已存在聊天,确认是否已存在对方发起，确认目标用户是否为自己
        if (iTcUserService.count(queryWrapper)>0 && iTcChatService.count(queryWrapper1)==0 && iTcChatService.count(queryWrapper2)==0 && !userId.equals(id)){
            iTcChatService.save(tcChat);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Description 删除聊天
     * Param 聊天ID(url)
     * Return 执行结果(bool)
     */
    @GetMapping("/delete")
    public ResponseEntity<String> delete(@RequestHeader HashMap hashMapHeader,Integer id){
        //根据token获取当前用户
        Integer userId = Integer.valueOf(JwtUtils.getClaimByToken((String) hashMapHeader.get("authorization")).getSubject());
        //检测聊天是否存在
        QueryWrapper<TcChat> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chat_send",userId).eq("chat_id",id);
        if (iTcChatService.count(queryWrapper)>0){
            iTcChatService.removeById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
