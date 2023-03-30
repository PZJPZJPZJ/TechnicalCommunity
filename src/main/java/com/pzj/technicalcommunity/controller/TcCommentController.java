package com.pzj.technicalcommunity.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pzj.technicalcommunity.entity.TcComment;
import com.pzj.technicalcommunity.entity.TcCommentDTO;
import com.pzj.technicalcommunity.service.ITcCommentService;
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
 * @since 2023-02-13
 */
@RestController
@RequestMapping("/comment")
public class TcCommentController {
    @Autowired
    private ITcCommentService iTcCommentService;

    /**
     * Description 时间倒叙分页展示当前帖子的所有评论
     * Param 分页数据，帖子ID(json)
     * Return 当前帖子所有评论(json)
     */
    @PostMapping("/list")
    public ResultPackage list(@RequestBody HashMap hashMap){
        //设置页数和页大小
        Page<TcCommentDTO> page = new Page<>();
        page.setCurrent((int)hashMap.get("pageNum"));
        page.setSize((int)hashMap.get("pageSize"));
        //执行自定义查询
        IPage<TcCommentDTO> iPage = iTcCommentService.pagePost(Integer.valueOf(hashMap.get("postId").toString()),page);
        return ResultPackage.pack(iPage.getRecords(), iPage.getTotal());
    }

    /**
     * Description 新建评论
     * Param 评论实体(json)
     * Return 执行结果(bool)
     */
    @PostMapping("/save")
    public boolean save(@RequestBody TcComment tcComment){
        return iTcCommentService.save(tcComment);
    }

    /**
     * Description 删除评论
     * Param 评论ID(url)
     * Return 执行结果(bool)
     */
    @GetMapping("/delete")
    public boolean delete(Integer id){
        return iTcCommentService.removeById(id);
    }
}
