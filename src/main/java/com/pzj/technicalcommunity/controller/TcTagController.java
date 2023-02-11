package com.pzj.technicalcommunity.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pzj.technicalcommunity.entity.TcTag;
import com.pzj.technicalcommunity.entity.TcUser;
import com.pzj.technicalcommunity.service.ITcTagService;
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
@RequestMapping("/tag")
public class TcTagController {
    @Autowired
    private ITcTagService iTcTagService;

    //分页查询标签信息
    @PostMapping("/page")
    public ResultPackage listPage(@RequestBody HashMap hashMap){
        Page<TcTag> page = new Page<>();
        page.setCurrent((int)hashMap.get("pageNum"));
        page.setSize((int)hashMap.get("pageSize"));
        IPage<TcTag> iPage = iTcTagService.page(page);
        return ResultPackage.success(iPage.getRecords(),iPage.getTotal());
    }

    //标签名模糊查询
    //新建帖子时可以键入选择，分类展示页面可以键入查询
    @PostMapping ("/search")
    public List<TcTag> search(@RequestBody TcTag tcTag){
        LambdaQueryWrapper<TcTag> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(TcTag::getTagName,tcTag.getTagName());
        return iTcTagService.list(lambdaQueryWrapper);
    }

    //用户新建标签
    //新建帖子的时候
    @PostMapping("/save")
    public boolean save(@RequestBody TcTag tcTag){
        return iTcTagService.save(tcTag);
    }

    //修改标签信息
    //管理员维护标签
    @PostMapping("/update")
    public boolean update(@RequestBody TcTag tcTag){
        return iTcTagService.updateById(tcTag);
    }

    //删除标签
    //管理员维护标签
    @PostMapping("/mod")
    public boolean delete(Integer id){
        return iTcTagService.removeById(id);
    }
}
