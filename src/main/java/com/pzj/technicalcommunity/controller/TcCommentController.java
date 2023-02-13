package com.pzj.technicalcommunity.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pzj.technicalcommunity.entity.TcComment;
import com.pzj.technicalcommunity.service.ITcCommentService;
import com.pzj.technicalcommunity.util.ResultPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pzj
 * @since 2023-02-13
 */
@RestController
@RequestMapping("/comment")
public class TcCommentController {
    @Autowired
    private ITcCommentService iTcCommentService;

    /**
     * Description 时间倒叙展示当前帖子的所有评论
     * Param 分页数据，帖子ID(json)
     * Return 当前帖子所有评论(json)
     */
    public ResultPackage list(@RequestBody HashMap hashMap){
        //设置页数和页大小
        Page<TcComment> page = new Page<>();
        page.setCurrent((int)hashMap.get("pageNum"));
        page.setSize((int)hashMap.get("pageSize"));
        //设置查询条件
        //设置查询条件
        QueryWrapper<TcComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("comment_post",hashMap.get("postId")).orderByDesc("comment_time");
        //执行查询
        List<TcComment> list = iTcCommentService.list(queryWrapper);
        return ResultPackage.pack(list);
    }

    /**
     * Description 新建帖子
     * Param 帖子实体(json)
     * Return 执行结果(bool)
     */
    @PostMapping("/save")
    public boolean save(@RequestBody TcComment tcComment){
        return iTcCommentService.save(tcComment);
    }

    /**
     * Description 删除帖子
     * Param 帖子ID(url)
     * Return 执行结果(bool)
     */
    @GetMapping("/delete")
    public boolean delete(Integer id){
        return iTcCommentService.removeById(id);
    }
}
