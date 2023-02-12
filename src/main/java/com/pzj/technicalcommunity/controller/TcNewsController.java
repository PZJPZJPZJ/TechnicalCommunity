package com.pzj.technicalcommunity.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pzj.technicalcommunity.entity.TcNews;
import com.pzj.technicalcommunity.entity.TcPost;
import com.pzj.technicalcommunity.service.ITcNewsService;
import com.pzj.technicalcommunity.util.ResultPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/tc-news")
public class TcNewsController {
    @Autowired
    private ITcNewsService iTcNewsService;

    /**
     * Description 展示最新10条新闻
     * Param 无
     * Return 当前标签所有帖子(json)
     */
    @GetMapping("/list")
    public ResultPackage list(){
        //设置查询条件
        QueryWrapper<TcNews> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("news_time");
        //执行查询
        List<TcNews> list = iTcNewsService.list(queryWrapper);
        return ResultPackage.pack(list);
    }

}
