package com.pzj.technicalcommunity.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pzj.technicalcommunity.entity.TcPost;
import com.pzj.technicalcommunity.service.ITcPostService;
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
 * @since 2023-02-06
 */
@RestController
@RequestMapping("/post")
public class TcPostController {
    @Autowired
    private ITcPostService iTcPostService;

    /**
     * Description 精确展示当前帖子
     * Param 帖子ID(url)
     * Return 当前帖子内容(json)
     */
    @RequestMapping("/show")
    public ResultPackage show(Integer id){
        //设置查询条件
        QueryWrapper<TcPost> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("post_id",id);
        //执行查询
        List<TcPost> list = iTcPostService.list(queryWrapper);
        return ResultPackage.pack(list);
    }

    /**
     * Description 模糊查询帖子
     * Param 分页数据,部分标题(json)
     * Return 当前标签所有帖子(json)
     */
    @RequestMapping("/search")
    public ResultPackage search(@RequestBody HashMap hashMap){
        //设置页数和页大小
        Page<TcPost> page = new Page<>();
        page.setCurrent((int)hashMap.get("pageNum"));
        page.setSize((int)hashMap.get("pageSize"));
        //设置查询条件
        QueryWrapper<TcPost> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("post_title",hashMap.get("postTitle"));
        //执行查询
        IPage<TcPost> iPage = iTcPostService.page(page,queryWrapper);
        return ResultPackage.pack(iPage.getRecords(),iPage.getTotal());
    }

    /**
     * Description 分页展示所有帖子
     * Param 分页数据(json)
     * Return 当前标签所有帖子(json)
     */
    @RequestMapping("/list")
    public ResultPackage page(@RequestBody HashMap hashMap){
        //设置页数和页大小
        Page<TcPost> page = new Page<>();
        page.setCurrent((int)hashMap.get("pageNum"));
        page.setSize((int)hashMap.get("pageSize"));
        //执行查询
        IPage<TcPost> iPage = iTcPostService.page(page);
        return ResultPackage.pack(iPage.getRecords(),iPage.getTotal());
    }

    /**
     * Description 随机展示首页热门帖子
     * Param 分页数据(json)
     * Return 随机热门帖子(json)
     */
    @RequestMapping("/hot")
    public ResultPackage hot(@RequestBody HashMap hashMap){
        //设置页数和页大小
        Page<TcPost> page = new Page<>();
        page.setCurrent((int)hashMap.get("pageNum"));
        page.setSize((int)hashMap.get("pageSize"));
        //设置随机查询热门条件
        QueryWrapper<TcPost> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("post_top",1).last("ORDER BY RAND()");
        //执行查询
        IPage<TcPost> iPage = iTcPostService.page(page,queryWrapper);
        return ResultPackage.pack(iPage.getRecords(),iPage.getTotal());
    }

    /**
     * Description 时间倒叙展示当前用户所有已发布帖子
     * Param 分页数据(json)
     * Return 用户帖子(json)
     */
    @RequestMapping("/user")
    public ResultPackage user(@RequestBody HashMap hashMap){
        //设置页数和页大小
        Page<TcPost> page = new Page<>();
        page.setCurrent((int)hashMap.get("pageNum"));
        page.setSize((int)hashMap.get("pageSize"));
        //设置查询条件
        QueryWrapper<TcPost> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("post_user",hashMap.get("postUser")).orderByDesc("post_time");
        //执行查询
        IPage<TcPost> iPage = iTcPostService.page(page,queryWrapper);
        return ResultPackage.pack(iPage.getRecords(),iPage.getTotal());
    }

    /**
     * Description 统计当前Tag帖子数
     * Param 标签ID(url)
     * Return 数量(json)
     */
    @PostMapping("/count")
    public ResultPackage count(Integer id){
        //设置查询条件
        QueryWrapper<TcPost> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("post_tag",id);
        //执行查询
        Integer integer = iTcPostService.count(queryWrapper);
        return ResultPackage.pack(integer);
    }

    /**
     * Description 置顶当前帖子
     * Param 帖子ID(url)
     * Return 执行结果(bool)
     */
    @GetMapping("/top")
    public boolean top(Integer id){
        //设置实体数据
        TcPost tcPost = new TcPost();
        tcPost.setPostId(id);
        tcPost.setPostTop(true);
        return iTcPostService.updateById(tcPost);
    }

    /**
     * Description 取消置顶当前帖子
     * Param 帖子ID(url)
     * Return 执行结果(bool)
     */
    @GetMapping("/bottom")
    public boolean bottom(Integer id){
        //设置实体数据
        TcPost tcPost = new TcPost();
        tcPost.setPostId(id);
        tcPost.setPostTop(false);
        return iTcPostService.updateById(tcPost);
    }

    /**
     * Description 新建帖子
     * Param 帖子实体(json)
     * Return 执行结果(bool)
     */
    @PostMapping("/save")
    public boolean save(@RequestBody TcPost tcPost){
        return iTcPostService.save(tcPost);
    }

    /**
     * Description 删除帖子
     * Param 帖子ID(url)
     * Return 执行结果(bool)
     */
    @GetMapping("/delete")
    public boolean delete(Integer id){
        return iTcPostService.removeById(id);
    }

}
