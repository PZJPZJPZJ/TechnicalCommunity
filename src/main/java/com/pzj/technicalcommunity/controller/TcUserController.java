package com.pzj.technicalcommunity.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pzj.technicalcommunity.entity.TcUser;
import com.pzj.technicalcommunity.service.ITcUserService;
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
@RequestMapping("/user")
public class TcUserController {
    @Autowired
    private ITcUserService iTcUserService;
    //列出用户信息
    @GetMapping ("/list")
    public List<TcUser> list(){
        return iTcUserService.list();
    }

    //分页查询用户信息
    @PostMapping("/page")
    public List<TcUser> listPage(@RequestBody HashMap hashMap){
        Page<TcUser> page = new Page<>();
        page.setCurrent((int)hashMap.get("pageNum"));
        page.setSize((int)hashMap.get("pageSize"));
        IPage<TcUser> iPage = iTcUserService.page(page);
        return iPage.getRecords();
    }

    //模糊查询用户信息
    @PostMapping ("/search")
    public List<TcUser> search(@RequestBody TcUser tcUser){
        LambdaQueryWrapper<TcUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(TcUser::getUserName,tcUser.getUserName());
        return iTcUserService.list(lambdaQueryWrapper);
    }

    //新增用户信息
    @PostMapping("/save")
    public boolean save(@RequestBody TcUser tcUser){
        return iTcUserService.save(tcUser);
    }

    //修改用户信息
    @PostMapping("/update")
    public boolean update(@RequestBody TcUser tcUser){
        return iTcUserService.updateById(tcUser);
    }

    //新增或修改用户信息
    @PostMapping("/mod")
    public boolean mod(@RequestBody TcUser tcUser){
        return iTcUserService.saveOrUpdate(tcUser);
    }

    //删除用户信息
    @GetMapping("/delete")
    public boolean delete(Integer id){
        return iTcUserService.removeById(id);
    }
}
