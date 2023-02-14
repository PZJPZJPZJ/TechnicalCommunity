package com.pzj.technicalcommunity.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pzj.technicalcommunity.entity.TcNews;
import com.pzj.technicalcommunity.service.ITcNewsService;
import com.pzj.technicalcommunity.util.ResultPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/news")
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
        queryWrapper.orderByDesc("news_time").last("limit 10");
        //执行查询
        List<TcNews> list = iTcNewsService.list(queryWrapper);
        return ResultPackage.success(list);
    }

    /**
     * Description 新增新闻
     * Param 新闻实体(json)
     * Return 执行结果(bool)
     */
    @PostMapping("/save")
    public boolean save(@RequestBody TcNews tcNews){
        return iTcNewsService.save(tcNews);
    }

    /**
     * Description 修改新闻
     * Param 新闻实体(json)
     * Return 执行结果(bool)
     */
    @PostMapping("/update")
    public boolean update(@RequestBody TcNews tcNews){
        return iTcNewsService.updateById(tcNews);
    }

    /**
     * Description 新增或修改新闻
     * Param 新闻实体(json)
     * Return 执行结果(bool)
     */
    @PostMapping("/mod")
    public boolean mod(@RequestBody TcNews tcNews){
        return iTcNewsService.saveOrUpdate(tcNews);
    }

    /**
     * Description 删除新闻
     * Param 新闻ID(url)
     * Return 执行结果(bool)
     */
    @GetMapping("/delete")
    public boolean delete(Integer id){
        return iTcNewsService.removeById(id);
    }
}
