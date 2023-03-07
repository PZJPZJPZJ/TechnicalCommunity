package com.pzj.technicalcommunity.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pzj.technicalcommunity.entity.TcTag;
import com.pzj.technicalcommunity.service.ITcTagService;
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
@RequestMapping("/tag")
public class TcTagController {
    @Autowired
    private ITcTagService iTcTagService;

    /**
     * 获取全部标签
     * @return
     */
    @GetMapping("/list")
    public ResultPackage list(){
        return ResultPackage.pack(iTcTagService.list());
    }

    /**
     * Description 分页查询标签信息
     * Param 分页信息(json)
     * Return 分页标签(json)
     */
    @PostMapping("/page")
    public ResultPackage listPage(@RequestBody HashMap hashMap){
        Page<TcTag> page = new Page<>();
        page.setCurrent((int)hashMap.get("pageNum"));
        page.setSize((int)hashMap.get("pageSize"));
        IPage<TcTag> iPage = iTcTagService.page(page);
        return ResultPackage.pack(iPage.getRecords(),iPage.getTotal());
    }

    /**
     * Description 展示标签键入查询
     * Param 分页信息，查询关键词(json)
     * Return 查询结果(json)
     */
    @GetMapping ("/search")
    public ResultPackage search(String str){
        //设置查询条件
        QueryWrapper<TcTag> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("tag_name",str);
        //执行查询
        return ResultPackage.pack(iTcTagService.list(queryWrapper));
    }

    /**
     * Description 用户新建标签
     * Param 标签实体(json)
     * Return 执行结果(bool)
     */
    @PostMapping("/save")
    public boolean save(@RequestBody TcTag tcTag){
        return iTcTagService.save(tcTag);
    }

    /**
     * Description 管理员修改标签信息
     * Param 标签实体(json)
     * Return 执行结果(bool)
     */
    @PostMapping("/update")
    public boolean update(@RequestBody TcTag tcTag){
        return iTcTagService.updateById(tcTag);
    }

    /**
     * Description 管理员维护标签
     * Param 标签ID(url)
     * Return 执行结果(bool)
     */
    @PostMapping("/mod")
    public boolean delete(Integer id){
        return iTcTagService.removeById(id);
    }
}
